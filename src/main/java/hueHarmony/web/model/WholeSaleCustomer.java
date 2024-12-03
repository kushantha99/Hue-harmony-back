package hueHarmony.web.model;

import hueHarmony.web.model.enums.WholeSaleCustomerStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "whole_sale_customer")
@Builder
public class WholeSaleCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wholeSaleCustomerId;

    @Column(name = "land_phone", columnDefinition = "VARCHAR", nullable = false, length = 15)
    private String landPhone;

    @Column(name = "address", columnDefinition = "VARCHAR", nullable = false)
    private String address;

    @Column(name = "nic_no", columnDefinition = "VARCHAR", nullable = false, length = 12)
    private String nicNo;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "whole_sale_customer_status", columnDefinition = "VARCHAR", nullable = false, length = 10)
    private WholeSaleCustomerStatus wholeSaleCustomerStatus = WholeSaleCustomerStatus.PENDING;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "delivery_address", columnDefinition = "VARCHAR", nullable = true)
    private String deliveryAddress;

    @Column(name = "business_address", columnDefinition = "VARCHAR", nullable = true)
    private String businessAddress;

    @Column(name = "contact_person", columnDefinition = "VARCHAR", nullable = true)
    private String contactPerson;

    @Column(name = "business_name", columnDefinition = "VARCHAR", nullable = true)
    private String businessName;

    @Column(name = "contact_person_number", columnDefinition = "VARCHAR", nullable = true)
    private Integer contactPersonNumber;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WholeSaleOrder> orders;
}
