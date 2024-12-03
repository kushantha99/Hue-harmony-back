//package hueHarmony.web.dto;
//
//
//import hueHarmony.web.model.enums.data_set.*;
//import jakarta.persistence.Column;
//import lombok.Data;
//
//import java.util.List;
//
//@Data
//public class AddProductDto {
//
//    private String productName;
//
//    private int productSize;
//
//    private String productDescription;
//
//    private float productPrice;
//
//    private float productDiscount;
//
//    private int coat;
//
//    private int dryingTime;
//
//    private int coverage;
//
//    private int onlineLimit;
//
//    private int productQuantity;
//
//    private ProductStatus productStatus;
//
//    private Brands brand;
//
//    private Finish finish;
//
//    private List<String> roomType;
//
//    private List<String> productTypes;
//
//    private List<String> surfaces;
//
//    private List<String> positions;
//
//    private List<String> productFeatures;
//
//    private List<String> productImage;
//}

package hueHarmony.web.dto;


import hueHarmony.web.model.enums.data_set.*;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductDto {

    private String productName;

    private int productSize;

    private String productColor;

    private String productDescription;

    private float productPrice;

    private float productDiscount;

    private int coat;

    private int dryingTime;

    private int coverage;

    private int onlineLimit;

    private int productQuantity;

    private ProductStatus productStatus;

    private Brands brand;

    private Finish finish;

    private List<String> roomType;

    private List<String> productTypes;

    private List<String> surfaces;

    private List<String> positions;

    private List<String> productFeatures;

    private List<String> productImage;
}
