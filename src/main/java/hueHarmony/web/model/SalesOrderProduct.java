package hueHarmony.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sales_order_product")
@Builder
public class SalesOrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderProductId;

    @Column(name = "quantity", nullable = false, columnDefinition = "SMALLINT DEFAULT 0 CHECK(quantity > 0)")
    private int quantity;

//    @Column(name = "discount", nullable = false, columnDefinition = "REAL DEFAULT 0 CHECK(discount >= 0)")
//    private float discount;

//    add trigger to update full price when order product created or updated quantity of existing order product.
//    use insert update BEFORE trigger(nullable is false)
    @Column(name = "full_price", nullable = false, columnDefinition = "REAL DEFAULT 0 CHECK(full_price >= 0)")
    private float fullPrice;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false) // Matches the primary key of WholeSaleOrder
    private WholeSaleOrder wholeSaleOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
