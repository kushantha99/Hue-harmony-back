package hueHarmony.web.dto;

import hueHarmony.web.model.SalesOrderProduct;
import hueHarmony.web.model.WholeSaleCustomer;
import hueHarmony.web.model.enums.OrderStatus;
import hueHarmony.web.model.enums.PaymentMethod;
import hueHarmony.web.model.enums.PaymentStatus;
import hueHarmony.web.model.enums.ShipmentVariationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WholeSaleOrderdto {

    private Long customer;  // Reference to the customer placing the order
    private Date orderDate;  // Date when the order was placed
    private Date shipmentDate;
    private ShipmentVariationStatus shipmentVariationStatus;// Date when the order was shipped// Status of payment (e.g., "paid", "pending", "overdue")
    private Double totalAmount;  // Total amount of the order
    private Double discountAmount;  // Any discount applied to the order
    private String billingAddress;  // Billing address for the order
    private PaymentMethod paymentMethod;  // Payment method (e.g., "credit card", "bank transfer")
    private String orderNotes;  // Any special instructions or notes for the order
    private List<SalesOrderProductDto> orderItems;
}
