package hueHarmony.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_whole_sale_customer")
public class OrderWholeSaleCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderWholeSaleCustomerId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "whole_sale_customer_id", nullable = false)
    private WholeSaleCustomer wholeSaleCustomer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "approved_user_id", nullable = false)
    private User approvedUser;
}
