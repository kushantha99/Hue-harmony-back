package hueHarmony.web.service;

import hueHarmony.web.model.PurchaseOrder;
import hueHarmony.web.repository.PurchaseOrderRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder findPurchaseOrderById(Long id) {
//        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(id);
        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.findByPurchaseOrderId(id);
//        if (purchaseOrder.isPresent()) {
//            PurchaseOrder savedPurchaseOrder = purchaseOrder.get();
//            Hibernate.initialize(savedPurchaseOrder.getSupplier());
//
//            return savedPurchaseOrder;
//        }
        Hibernate.initialize(savedPurchaseOrder.getSupplier());

        return savedPurchaseOrder;
//        try {
//            throw new Exception("Purchase Order does not exist");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder purchaseOrderRequest) {
        return purchaseOrderRepository.save(purchaseOrderRequest);

    }

    public PurchaseOrder updateOrderStatus(Long id, PurchaseOrder statusRequest) {
        PurchaseOrder purchaseOrder = findPurchaseOrderById(id);

        purchaseOrder.setStatus(statusRequest.getStatus());
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public void deletePurchaseOrder(Long id) {

        PurchaseOrder purchaseOrder = findPurchaseOrderById(id);

        purchaseOrderRepository.delete(purchaseOrder);
    }
}
