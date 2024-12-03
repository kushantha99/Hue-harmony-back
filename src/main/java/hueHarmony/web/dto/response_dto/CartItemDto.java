package hueHarmony.web.dto.response_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private int cartItemId;
    private int quantity;
    private String itemImage;
    private float fullPrice;
    private float price;
    private String productName;
    private int productId;
    private int userId;
}
