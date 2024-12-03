package hueHarmony.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PosProductDto {
    private int productId;
    private String productName;
    private MultipartFile[] productImages;
    private Float productDiscount;
    private Float productPrice;

    public PosProductDto(int productId, String productName, Float productPrice, Float productDiscount) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
    }

}
