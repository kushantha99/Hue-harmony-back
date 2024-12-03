package hueHarmony.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase_ordder_supplier_variation")
public class PurchaseOrderSupplierVariation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int purchaseOrderSupplierVariationId;

    @Column(name = "quantity", columnDefinition = "SMALLINT DEFAULT 0 CHECK(quantity >= 0)", nullable = false)
    private int quantity;

    @Column(name = "unit_cost", columnDefinition = "REAL DEFAULT 0 CHECK(unit_cost >= 0)", nullable = false)
    private float unitCost;

    @Column(name = "discount", columnDefinition = "REAL DEFAULT 0 CHECK(discount >= 0)")
    private float discount;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

//    @ManyToOne
//    @JoinColumn(name = "supplier_variation_id")
//    private SupplierVariation supplierVariation;
}
