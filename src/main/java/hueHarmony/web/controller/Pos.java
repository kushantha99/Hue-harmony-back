package hueHarmony.web.controller;

import hueHarmony.web.dto.*;
import hueHarmony.web.dto.response.PosDisplayDto;
import hueHarmony.web.model.PosOrder;
import hueHarmony.web.service.PosService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pos")
@RequiredArgsConstructor
public class Pos {

    private final PosService posService;

    @GetMapping("/get-products")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BACKOFFICE', 'ROLE_SALESMANAGER')")
    public ResponseEntity<Object> getProducts() {
        try{
            List<PosProductDto> products = posService.getProducts();
//           return ResponseEntity.status(200).body("Supplier status update successfully.");
            return ResponseEntity.status(200).body(products);

        }catch(Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }


//    @GetMapping("/filter-products")
////    @PreAuthorize("hasAnyRole('ROLE_CACHIER')")
//    public ResponseEntity<Object> posFilterProducts(@ModelAttribute FilterProductDto productFilterDto){
//        try{
//            Page<PosDisplayDto> displayDtos = posService.posFilterProductsForList(productFilterDto);
////
//            if(displayDtos.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
//
//            return ResponseEntity.status(HttpStatus.OK).body(displayDtos);
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body("Internal server error!!! Please try again later...");
//        }
//    }

    @PostMapping("/create-order")
    public ResponseEntity<Object> createOrder(@RequestBody PosOrderDto order) {
        try {
            PosOrder createdOrder = posService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order Created");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/get-sales-summary/{cashierId}")
    public ResponseEntity<Object> getTotalsForCashierOnDate(@PathVariable Long cashierId) {
        try {
            //LocalDate parsedDate = LocalDate.parse(date); // Ensure the date format is "yyyy-MM-dd"
            SalesSummaryDto totals = posService.getTotalsForCashierOnDate(cashierId);
            return ResponseEntity.ok(totals);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @GetMapping("/get-orders/{cashierId}")
    public ResponseEntity<List<PosOrderListDto>> getCompletedOrdersByCashier(@PathVariable Long cashierId) {
        try {
            // Fetch completed orders for the given cashier
            List<PosOrderListDto> orders = posService.getCompletedOrdersByCashier(cashierId);

            // If no orders found, return a 404 not found response
            if (orders.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Return the list of orders with a 200 OK response
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            // Handle any exceptions and return a 500 internal server error
            return ResponseEntity.status(500).body(null);
        }
    }
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<PosOrder> getOrderById(@PathVariable Long orderId) {
        try {
            // Fetch the order by ID using the service method
            Optional<PosOrder> order = posService.getOrderById(orderId);

            // If order is not found, return a 404 Not Found response
            return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

            // Return the order details with a 200 OK response
        } catch (Exception e) {
            // In case of any error, return a 500 Internal Server Error
            return ResponseEntity.status(500).body(null);
        }
    }
//    @GetMapping("/get-orders/{cashierId}")
//    public ResponseEntity<PosOrder> getOrderList(@PathVariable Long cashierId) {
//        try {
//            // Fetch the order by ID using the service method
//            Optional<PosOrder> order = posService.getOrderById(orderId);
//
//            // If order is not found, return a 404 Not Found response
//            return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//
//            // Return the order details with a 200 OK response
//        } catch (Exception e) {
//            // In case of any error, return a 500 Internal Server Error
//            return ResponseEntity.status(500).body(null);
//        }
//    }


}
