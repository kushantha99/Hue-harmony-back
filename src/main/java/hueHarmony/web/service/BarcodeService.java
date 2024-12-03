package hueHarmony.web.service;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;  // Import BufferedImage
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class BarcodeService {

    public byte[] generateBarcode(String productCode) {
        try {
            Code39Bean barcodeBean = new Code39Bean();
            barcodeBean.setModuleWidth(0.2);  // Set the width of the barcodes
            barcodeBean.setHeight(15f);       // Set the height of the barcodes
            barcodeBean.doQuietZone(true);    // Add quiet zones (margins around the barcode)

            // Stream to store the barcode image data
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BitmapCanvasProvider canvasProvider = new BitmapCanvasProvider(outputStream, "image/png", 200,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);  // Use BufferedImage.TYPE_BYTE_BINARY

            // Generate the barcode
            barcodeBean.generateBarcode(canvasProvider, productCode);
            canvasProvider.finish();

            return outputStream.toByteArray();  // Return barcode image as byte array
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

