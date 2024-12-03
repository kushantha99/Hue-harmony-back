package hueHarmony.web.dto;

import com.fasterxml.jackson.annotation.JsonView;
import hueHarmony.web.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariationDto {

    public interface onCreate{}
    public interface onUpdate{}
    public interface onDelete{}
    public interface onView{}
    public interface onStatusUpdate{}


    @JsonView({onCreate.class,onDelete.class,onUpdate.class})
    private float unitPrice;

    @JsonView({onCreate.class,onDelete.class,onUpdate.class})
    private Product product;

    @JsonView({onCreate.class,onDelete.class,onUpdate.class})
    private int reOrderPoint;

    @JsonView({onCreate.class,onDelete.class,onUpdate.class})
    private int size;

    @JsonView({onCreate.class,onDelete.class,onUpdate.class})
    private String Color;

}
