package hueHarmony.web.dto;

import hueHarmony.web.model.enums.data_set.Brands;
import hueHarmony.web.model.enums.data_set.Finish;
import hueHarmony.web.model.enums.data_set.Position;
import hueHarmony.web.model.enums.data_set.ProductStatus;
import hueHarmony.web.model.enums.data_set.ProductType;
import hueHarmony.web.model.enums.data_set.RoomType;
import hueHarmony.web.model.enums.data_set.Surface;
import lombok.Data;

import java.util.List;

@Data
public class UpdateProductDto {
    private int productId;
    private String productName;

    private String productDescription;

    private float productPrice;

    private float productDiscount;

    private int coat;

    private int dryingTime;

    private int coverage;

    private float onlineLimit;

    private float productQuantity;

    private String productStatus;

    private Brands brand;

    private Finish finish;

    private List<String> roomType;

    private List<String> productTypes;

    private List<String> surfaces;

    private List<String> positions;

    private List<String> productFeatures;

    private List<String> productImages;
}

