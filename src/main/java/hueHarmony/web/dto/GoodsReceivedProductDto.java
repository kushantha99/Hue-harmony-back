package hueHarmony.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsReceivedProductDto {

    private int id;
    private int grnId;
    private int productId;
    private int quantityReceived;
}
