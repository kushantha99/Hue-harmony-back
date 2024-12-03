package hueHarmony.web.model;

import hueHarmony.web.model.enums.SupplierStatus;
import hueHarmony.web.model.enums.SupplierType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplierId;

    @Column(name = "supplier_name", columnDefinition = "VARCHAR", length = 100, nullable = false)
    private String supplierName;

    @Column(name = "supplier_address", columnDefinition = "VARCHAR", length = 200, nullable = false)
    private String supplierAddress;

    @Column(name = "supplier_mobile_phone", columnDefinition = "VARCHAR", length = 15)
    private String supplierMobilePhone;

    @Column(name = "supplier_email", columnDefinition = "VARCHAR", length = 100)
    private String supplierEmail;

    @Column(name = "supplier_land_phone", columnDefinition = "VARCHAR", length = 15, nullable = false)
    private String supplierLandPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "supplier_type", nullable = false)
    private SupplierType supplierType = SupplierType.LOCAL;

    @Enumerated(EnumType.STRING)
    @Column(name = "supplier_status", nullable = false)
    private SupplierStatus supplierStatus = SupplierStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user_id")
    private User createdUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_user_id")
    private User approvedUser;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SupplierProduct> products;

}
