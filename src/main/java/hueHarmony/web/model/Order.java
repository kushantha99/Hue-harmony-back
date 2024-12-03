package hueHarmony.web.model;

import hueHarmony.web.model.enums.OrderStatus;
import hueHarmony.web.model.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column(name = "order_no", nullable = false, unique = true)
    @Builder.Default
    private String orderNo = generateUniqueOrderNumber();

    @Column(name = "order_note", columnDefinition = "TEXT")
    private String orderNote;

    @Column(name = "order_discount", columnDefinition = "REAL DEFAULT 0 CHECK(order_discount >= 0)")
    private float orderDiscount;

    @Column(name = "product_published_time", nullable = false, columnDefinition = "TIMESTAMP")
    @Builder.Default
    private LocalDateTime productPublishedTime = LocalDateTime.now();

    @Column(name = "order_status", columnDefinition = "VARCHAR", length = 25, nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.CREATED;

    @Column(name = "order_payment_method", columnDefinition = "VARCHAR", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod orderPaymentMethod;

    @OneToMany
    private List<Product> products;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
    private OnlineOrder onlineOrder;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
    private List<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Payment> orderPayments;
//
//    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    //    @JoinColumn(name = "order_id", nullable = false)
//    private List<PROJ.VIVO.model.OrderStatus> orderStatusTimeLine;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User createdUser;





    @Transient
    private static final Random random = new Random();

    @Transient
    private static String generateUniqueOrderNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timestamp = dateFormat.format(new Date());
        int randomNum = random.nextInt(10000);
        return timestamp + String.format("%04d", randomNum);
    }

//    @PrePersist
//    public void prePersistOrderNo(){
//        if(this.orderNo==0) this.orderNo = generateUniqueOrderNumber();
//    }
}
