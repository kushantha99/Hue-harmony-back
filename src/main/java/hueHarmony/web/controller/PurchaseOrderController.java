package hueHarmony.web.controller;

import hueHarmony.web.model.PurchaseOrder;
import hueHarmony.web.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase-order")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

//    @PostMapping("/create")
////    @PreAuthorize("hasAnyRole('ROLE_INVENTORYMANAGER', 'ROLE_BACKOFFICE')")
//    public PurchaseOrder createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
//        return purchaseOrderService.savePurchaseOrder(purchaseOrder);
//    }

    @PostMapping("/create")
//  @PreAuthorize("hasAnyRole('ROLE_INVENTORYMANAGER', 'ROLE_BACKOFFICE')")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrderRequest) {
        try {
            PurchaseOrder savedOrder = purchaseOrderService.savePurchaseOrder(purchaseOrderRequest);
            return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("view/{id}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderById( @PathVariable Long id) {
        try{
            PurchaseOrder purchaseOrder = purchaseOrderService.findPurchaseOrderById(id);
            return new ResponseEntity<>(purchaseOrder, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("update/{id}")
    public ResponseEntity<PurchaseOrder> updatePurchaseOrder(@RequestBody PurchaseOrder purchaseOrderRequest,
                                                             @PathVariable Long id) {

        try{
            PurchaseOrder purchaseOrder = purchaseOrderService.updatePurchaseOrder(id, purchaseOrderRequest);
            return new ResponseEntity<>(purchaseOrder, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update/status/{id}")
    public ResponseEntity<PurchaseOrder> updatePurchaseOrderStatus(@PathVariable Long id,
                                                                   @RequestBody PurchaseOrder statusRequest) {
        try{
            PurchaseOrder purchaseOrder = purchaseOrderService.updateOrderStatus(id,statusRequest);
            if(purchaseOrder == null) {
                throw new Exception("Purchase Order Not Found");
            }
            return new ResponseEntity<>(purchaseOrder, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePurchaseOrder(@PathVariable Long id) {
        try{
            purchaseOrderService.deletePurchaseOrder(id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
