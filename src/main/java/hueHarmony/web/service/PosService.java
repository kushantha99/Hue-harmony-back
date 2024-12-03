package hueHarmony.web.service;

import hueHarmony.web.controller.Product;
import hueHarmony.web.dto.*;
import hueHarmony.web.model.Loyalty;
import hueHarmony.web.model.PosOrder;
import hueHarmony.web.model.PosOrderItem;
import hueHarmony.web.model.enums.OrderStatus;
import hueHarmony.web.repository.LoyaltyRepository;
import hueHarmony.web.repository.PosOrderRepository;
import hueHarmony.web.repository.ProductRepository;
import hueHarmony.web.util.FunctionsUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PosService {

    private final Product product;
    @PersistenceContext
    private EntityManager entityManager;
    private final ProductRepository productRepository;
    private final PosOrderRepository posOrderRepository;
    private final LoyaltyRepository loyaltyRepository;

    public List<PosProductDto> getProducts() {
        return productRepository.getProducts();
    }

@Transactional
    public PosOrder createOrder(PosOrderDto orderDto) {
        // Map DTO fields to Entity using Builder
        PosOrder order = PosOrder.builder()
               .orderDate(orderDto.getOrderDate())
                //.customerName(orderDto.getCustomerName())
                .phoneNumber(orderDto.getPhoneNumber())
                .total(orderDto.getTotal())
                .subTotal(orderDto.getSubTotal())
                .discount(orderDto.getDiscount())
                .paymentMethod(orderDto.getPaymentMethod())
                .orderStatus(orderDto.getOrderStatus() != null ? orderDto.getOrderStatus() : OrderStatus.COMPLETED)
                .cashierId(orderDto.getCashierId())
                .build();

        // Add logic to handle items (if applicable)
        if (orderDto.getItems() != null && !orderDto.getItems().isEmpty()) {
            List<PosOrderItem> items = orderDto.getItems().stream()
                    .map(itemDto -> PosOrderItem.builder()
                            .product(entityManager.getReference(hueHarmony.web.model.Product.class,itemDto.getProductId()))
                            .order(order)
                            .price(itemDto.getPrice())
                            .quantity(itemDto.getQuantity())
                            .build())
                    .toList();
            order.setItems(items);
        }
        int points = FunctionsUtil.calculateLoyaltyPoints(orderDto.getSubTotal());
        int updatedRows = loyaltyRepository.updateLoyaltyPoints(orderDto.getPhoneNumber(), points);

        if (updatedRows == 0) {
            Loyalty newLoyalty = new Loyalty();
            newLoyalty.setContactNo(orderDto.getPhoneNumber());
            newLoyalty.setLoyaltyPoints(points);
            loyaltyRepository.save(newLoyalty);
        }


        // Save the order to the database (assuming you have a repository)
        return posOrderRepository.save(order);
    }


//public SummaryResponseDto getTotalsForCashierOnDate(Long cashierId, LocalDate parsedDate) {
//    // Use the parsedDate to get the start and end of the day
//    LocalDateTime startOfDay = parsedDate.atStartOfDay();
//    LocalDateTime endOfDay = parsedDate.atTime(23, 59, 59);
//
//    // Fetch orders for the cashier on the given date
//    List<PosOrder> orders = posOrderRepository.findByCashierAndOrderDateBetween(cashierId, startOfDay, endOfDay);
//
//    // Initialize totals
//    float total = 0;
//    float cashTotal = 0;
//    float cardTotal = 0;
//    float discountTotal = 0;
//
//    // Loop through orders and calculate totals
//    for (PosOrder order : orders) {
//        total = total.add(order.getTotal());
//        discountTotal = discountTotal.add(order.getDiscount());
//
//        if ("Cash".equalsIgnoreCase(order.getPaymentMethod())) {
//            cashTotal = cashTotal.add(order.getTotal());
//        } else if ("Card".equalsIgnoreCase(order.getPaymentMethod())) {
//            cardTotal = cardTotal.add(order.getTotal());
//        }
//    }
//
//    // Return the totals in a response object
//    return new SummaryResponseDto(total, cashTotal, cardTotal, discountTotal);
//}

    public List<PosOrderListDto> getCompletedOrdersByCashier(Long cashierId) {
        // Fetch completed orders for the cashier
        List<PosOrder> completedOrders = posOrderRepository.findByCashierAndOrderStatus(cashierId, OrderStatus.COMPLETED);

        // Map PosOrder list to PosOrderListDto list
        return completedOrders.stream()
                .map(this::mapToDto) // Use a mapping function to convert each PosOrder
                .collect(Collectors.toList());
    }

    private PosOrderListDto mapToDto(PosOrder posOrder) {
        // Map fields from PosOrder to PosOrderListDto
        return new PosOrderListDto(
                posOrder.getId(),
                posOrder.getTotal(),
                posOrder.getSubTotal(),
                posOrder.getDiscount(),
                posOrder.getOrderDate(),
                posOrder.getOrderStatus()
        );
    }

public Optional<PosOrder> getOrderById(Long orderId) {
    // Fetch the order by its ID, returns Optional to handle cases where the order might not exist
    return posOrderRepository.findById(orderId);
}


    public SalesSummaryDto getTotalsForCashierOnDate(Long cashierId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        List<PosOrder> todaySales = posOrderRepository.findByCashierAndOrderDateBetween(cashierId, startOfDay, endOfDay);

        float cashTotal = 0;
        float debitTotal = 0;
        float discountTotal = 0;

        for (PosOrder sale : todaySales) {
            if ("CASH".equalsIgnoreCase(sale.getPaymentMethod())) {
                cashTotal += sale.getTotal();
            } else if ("Debit Card".equalsIgnoreCase(sale.getPaymentMethod())) {
                debitTotal += sale.getTotal();
            }
            discountTotal += sale.getDiscount();
        }

        float totalSales = cashTotal + debitTotal;

        return new SalesSummaryDto(cashTotal, debitTotal, discountTotal, totalSales);
    }
}
