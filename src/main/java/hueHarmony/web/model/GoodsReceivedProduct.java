package hueHarmony.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "goods_received_product")
public class GoodsReceivedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grn_id")
    private GoodsReceivedNote goodsReceivedNote;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantityReceived;
}
