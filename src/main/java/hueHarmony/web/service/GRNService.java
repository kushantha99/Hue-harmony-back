package hueHarmony.web.service;

import hueHarmony.web.dto.GoodsReceivedNoteDto;
import hueHarmony.web.dto.GoodsReceivedProductDto;
import hueHarmony.web.model.*;
import hueHarmony.web.repository.GRNRepository;
import hueHarmony.web.repository.ProductRepository;
import hueHarmony.web.repository.PurchaseOrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GRNService {
    private final GRNRepository grnRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public void updateGRN(GoodsReceivedNoteDto grnDto, Long id) {
        GoodsReceivedNote grn = grnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Goods Received Note not found with ID: " + id));

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findByPurchaseOrderId(grnDto.getPurchaseOrderId());
        if (purchaseOrder == null) {
            throw new EntityNotFoundException("Purchase Order not found with ID: " + grnDto.getPurchaseOrderId());
        }
        grn.setPurchaseOrder(purchaseOrder);

        grn.setReceivedDate(grnDto.getReceivedDate() != null ? grnDto.getReceivedDate() : grn.getReceivedDate());
        grn.setRemarks(grnDto.getRemarks());
        grn.setComplete(grnDto.isComplete());

        List<GoodsReceivedProduct> updatedProducts = grnDto.getReceivedProducts().stream().map(productDto -> {
            GoodsReceivedProduct receivedProduct = grn.getReceivedProducts().stream()
                    .filter(existingProduct -> existingProduct.getProduct().getProductId() == productDto.getProductId())
                    .findFirst()
                    .orElse(new GoodsReceivedProduct());

            receivedProduct.setGoodsReceivedNote(grn);
            receivedProduct.setProduct(entityManager.getReference(Product.class, productDto.getProductId()));
            receivedProduct.setQuantityReceived(productDto.getQuantityReceived());
            return receivedProduct;
        }).toList();

        grn.getReceivedProducts().clear();
        grn.getReceivedProducts().addAll(updatedProducts);

        grnRepository.save(grn);
    }



    public GoodsReceivedNoteDto findGRNById(Long grnId) {
        GoodsReceivedNote goodsReceivedNote = grnRepository.findGoodsReceivedNoteByGrnId(grnId);
        if (goodsReceivedNote == null) {
            throw new EntityNotFoundException("Goods Received Note not found with ID: " + grnId);
        }

        GoodsReceivedNoteDto grnDto = new GoodsReceivedNoteDto();
        grnDto.setGrnId(grnId);
        grnDto.setReceivedDate(goodsReceivedNote.getReceivedDate());
        grnDto.setRemarks(goodsReceivedNote.getRemarks());
        grnDto.setPurchaseOrderId(goodsReceivedNote.getPurchaseOrder().getPurchaseOrderId());
        List<GoodsReceivedProductDto> receivedProductsDto = goodsReceivedNote.getReceivedProducts().stream()
                .map(product -> {
                    GoodsReceivedProductDto productDto = new GoodsReceivedProductDto();
                    productDto.setId(product.getId());
//                    productDto.setGrnId(product.getGrnId());
//                    productDto.setProductId(product.getProductId());
                    productDto.setQuantityReceived(product.getQuantityReceived());
                    return productDto;
                })
                .collect(Collectors.toList());

        // Set the list of received products in the GRN DTO
        grnDto.setReceivedProducts(receivedProductsDto);;
        grnDto.setComplete(goodsReceivedNote.isComplete());

        return grnDto;
    }



    public void createGRN(GoodsReceivedNoteDto grnDto){
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findByPurchaseOrderId(grnDto.getPurchaseOrderId());
        if (purchaseOrder == null) {
            throw new EntityNotFoundException("Purchase Order not found");
        }

        GoodsReceivedNote grn = new GoodsReceivedNote();
        grn.setPurchaseOrder(purchaseOrder);
        grn.setReceivedDate(grnDto.getReceivedDate() != null ? grnDto.getReceivedDate() : new Date());
        grn.setRemarks(grnDto.getRemarks());
        grn.setComplete(grnDto.isComplete());

        List<GoodsReceivedProduct> receivedProducts = grnDto.getReceivedProducts().stream().map(productDto -> {
            GoodsReceivedProduct receivedProduct = new GoodsReceivedProduct();
            receivedProduct.setGoodsReceivedNote(grn);
            receivedProduct.setProduct(entityManager.getReference(Product.class,productDto.getProductId() ));
            receivedProduct.setQuantityReceived(productDto.getQuantityReceived());
            return receivedProduct;
        }).collect(Collectors.toList());

        grn.setReceivedProducts(receivedProducts);
        grnRepository.save(grn);

    }

    public void deleteGRN(Long id) {
            GoodsReceivedNote grn = grnRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("GRN not found with ID: " + id));

            if (grn.getReceivedProducts() != null && !grn.getReceivedProducts().isEmpty()) {
                grn.getReceivedProducts().clear(); // Remove any relationships if necessary
            }

            grnRepository.delete(grn);


    }
}
