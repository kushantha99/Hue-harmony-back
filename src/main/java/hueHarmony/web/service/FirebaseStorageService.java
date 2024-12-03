package hueHarmony.web.service;

import com.google.api.client.util.Value;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class FirebaseStorageService {

    private final Storage storage;

    @Autowired
    public FirebaseStorageService(Storage storage){
        this.storage = storage;
    }

    @Getter
    private final static String bucketName = "hueharmony-1a8c2.appspot.com";


    public List<String> uploadImagesToFirebase(List<String> base64Images) {
        List<String> imageIds = new ArrayList<>();
        for (String base64Image : base64Images) {
            try {
                byte[] decodedBytes = Base64.getDecoder().decode(base64Image.split(",")[1]);
                String imageId = UUID.randomUUID().toString();
                String fileName = "images/" + imageId + ".jpg";

                Bucket bucket = StorageClient.getInstance().bucket(bucketName);
                bucket.create(fileName, decodedBytes, "image/jpeg");
                imageIds.add(imageId);

            } catch (Exception e) {
                throw new RuntimeException("Error uploading image to Firebase", e);
            }
        }
        return imageIds;
    }

    public List<String> getImageUrlsFromFirebase(List<String> imageIds) {
        if (imageIds == null || imageIds.isEmpty()) {
            return Collections.emptyList();
        }

        return imageIds.stream()
                .map(imageId -> {
                    try {
                        // Construct the file path for the image
                        String fileName = "images/" + imageId + ".jpg";

                        // Get the bucket reference
                        Bucket bucket = StorageClient.getInstance().bucket(bucketName);

                        // Retrieve the blob (file) from the bucket
                        Blob blob = bucket.get(fileName);

                        if (blob == null) {
                            throw new RuntimeException("Image not found for ID: " + imageId);
                        }

                        // Generate a signed URL for the image
                        URL signedUrl = blob.signUrl(7, TimeUnit.DAYS); // URL valid for 7 days
                        return signedUrl.toString();
                    } catch (Exception e) {
                        throw new RuntimeException("Error retrieving URL for image ID: " + imageId, e);
                    }
                })
                .collect(Collectors.toList());
    }




    public String uploadFile(String fileName, byte[] fileBytes, String contentType) {
        // Get the bucket instance from Firebase StorageClient
        var bucket = StorageClient.getInstance().bucket();

        // Create the BlobInfo object
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, fileName))
                .setContentType(contentType)
                .build();

        // Upload the file to the bucket
        bucket.create(blobInfo.getName(), fileBytes, blobInfo.getContentType());

        return blobInfo.getBlobId().getName();
    }

    public String getFileDownloadUrl(String blobId, int time, TimeUnit timeUnit) {
        BlobId blob = BlobId.of(bucketName, blobId);
        return storage.signUrl(BlobInfo.newBuilder(blob).build(), time, timeUnit, Storage.SignUrlOption.withV4Signature()).toString();
    }

    public void deleteFile(String blobId) {
//        String blobId;
//        try {
//            String path = (new URL(url)).getPath();
//            blobId = path.startsWith("/") ? path.substring(1) : path;
//        } catch (MalformedURLException e) {
//            throw new IllegalArgumentException("Invalid URL", e);
//        }
        BlobId blob = BlobId.of(bucketName, blobId);
        StorageClient.getInstance().bucket().getStorage().delete(blob);
    }

    public void deleteFileUsingUrl(String url) {
        String blobId;
        try {
            String path = (new URL(url)).getPath();
            blobId = path.startsWith("/") ? path.substring(1) : path;
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL", e);
        }
        BlobId blob = BlobId.of(bucketName, blobId);
        StorageClient.getInstance().bucket().getStorage().delete(blob);
    }
}

