package hueHarmony.web.dto.response;

import hueHarmony.web.model.enums.data_set.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PosDisplayDto extends ProductDisplayDto{

    private int stockCount;
    private boolean isStockAvailable;

    public PosDisplayDto(
            int productId,
            String productTitle,
            ProductStatus productStatus,
            String productImage,
            float price,
            float discount,
            int stockCount,
            boolean isStockAvailable
    ){
        super(productId, productTitle, productStatus, productImage, price, discount);
        this.stockCount = stockCount;
        this.isStockAvailable = isStockAvailable;
    }
}
