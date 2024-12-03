package hueHarmony.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "online_orders")
@Builder
public class OnlineOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int onlineOrderId;

    @Column(name = "waybill")
    private long waybill;

    @Column(name = "billing_address", columnDefinition = "TEXT", nullable = false)
    private String billingAddress;

    @Column(name = "shipping_address", columnDefinition = "TEXT", nullable = false)
    private String shippingAddress;

    @Column(name = "delivery_cost", nullable = false, columnDefinition = "REAL DEFAULT 0 CHECK(delivery_cost >= 0)")
    private float deliveryCost;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

//    @ManyToOne(cascade = CascadeType.DETACH)
//    @JoinColumn(name = "waybill_id")
//    private ActiveWaybill activeWaybill;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "delivery_service_id")
//    private DeliveryService deliveryService;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "district_id")
//    private District district;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "city_id")
//    private City city;
 }
