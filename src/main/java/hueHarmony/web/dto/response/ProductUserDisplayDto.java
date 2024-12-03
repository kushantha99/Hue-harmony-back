package hueHarmony.web.dto.response;

import hueHarmony.web.model.enums.data_set.ProductStatus;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUserDisplayDto extends ProductDisplayDto{

    private boolean isSale;
    private boolean isNew;
    private float reviewRate;
    private int totalReviews;
    private float discount;
    private ProductStatus productStatus;


    public ProductUserDisplayDto(
            int productId,
            String productTitle,
            ProductStatus productStatus,
            String productImageUrl,
            float price,
            boolean isSale,
            boolean isNew,
            float reviewRate,
            int totalReviews,
            float discount
    ){
        super(productId, productTitle, productStatus, productImageUrl,price,discount);
        this.isSale = isSale;
        this.isNew = isNew;
        this.reviewRate = reviewRate;
        this.totalReviews = totalReviews;

    }
}
