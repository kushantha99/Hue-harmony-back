package hueHarmony.web.dto;

import hueHarmony.web.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosOrderItemDto {
    private BigDecimal price;
    private int quantity;
    private Long productId;
}
