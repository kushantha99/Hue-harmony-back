package hueHarmony.web.dto;

import hueHarmony.web.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierProductDto {
    private int id;
    private int productId;
    private float price;

}
