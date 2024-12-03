package hueHarmony.web.dto.response;

import hueHarmony.web.model.enums.data_set.Brands;
import hueHarmony.web.model.enums.data_set.Finish;
import hueHarmony.web.model.enums.data_set.ProductStatus;
import hueHarmony.web.model.enums.data_set.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDisplayDto {

    private int productId;
    private String productName;
    private ProductStatus productStatus;
    private Brands brand;
    private RoomType roomType;
    private Finish finish;
    private float productPrice;
    private int productQuantity;


    public ProductDisplayDto(int productId, String productTitle, ProductStatus productStatus, String productImage, float price, float discount){
        this.productId = productId;
        this.productName = productTitle;
        this.productStatus = productStatus;
//        this.productImage = productImage;
    }

    public ProductDisplayDto(int productId, String productName, ProductStatus productStatus, Brands brand, Finish finish, float productPrice, float productQuantity){
        this.productId = productId;
        this.productName = productName;
        this.productStatus = productStatus;
        this.brand = brand;
        this.finish = finish;
        this.productPrice = productPrice;
        this.productQuantity = (int) productQuantity;
//        this.productImage = productImage;
    }
}
