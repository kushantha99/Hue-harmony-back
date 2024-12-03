package hueHarmony.web.model;

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

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "whole_sale_order")
public class WholeSaleOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generating orderId
    @Column(name = "order_id", nullable = false, unique = true)
    private Long orderId;  // Unique identifier for the order

    // Defining a Many-to-One relationship with WholeSaleCustomer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private WholeSaleCustomer customer;  // Reference to the customer placing the order

    @Temporal(TemporalType.DATE)  // Ensures it's stored as a DATE in DB
    @Column(name = "order_date", nullable = false)
    private Date orderDate;  // Date when the order was placed

    @Temporal(TemporalType.DATE)  // Ensures it's stored as a DATE in DB
    @Column(name = "shipment_date")
    private Date shipmentDate;  // Date when the order was shipped

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus = OrderStatus.CREATED;  // Status of the order (e.g., "pending", "completed")

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;  // Status of payment (e.g., "paid", "pending", "overdue")

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;  // Total amount of the order

    @Column(name = "discount_amount")
    private Double discountAmount;  // Any discount applied to the order

    @Column(name = "billing_address", length = 500)
    private String billingAddress;  // Billing address for the order

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = true)
    private PaymentMethod paymentMethod;  // Payment method (e.g., "credit card", "bank transfer")

    @Column(name = "order_notes", length = 1000)
    private String orderNotes;  // Any special instructions or notes for the order

    // One-to-Many relationship with SalesOrderProduct
    @OneToMany(mappedBy = "wholeSaleOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SalesOrderProduct> orderItems;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ShipmentVariationStatus shipmentVariationStatus = ShipmentVariationStatus.PENDING;

    @OneToOne(mappedBy = "wholeSaleOrder", fetch = FetchType.LAZY)
    private WholeSaleInvoice invoice;
}
