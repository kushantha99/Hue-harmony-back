package hueHarmony.web.dto;

import hueHarmony.web.annotation.validations.*;
import hueHarmony.web.model.enums.data_set.*;
import hueHarmony.web.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    public interface onCreate{}
    public interface onUpdate{}
    public interface onDelete{}

    @NotNull(groups = {onUpdate.class, onDelete.class})
    @DataExistingValidation(groups = {onUpdate.class, onDelete.class}, service = ProductService.class, method = "")
    private int productId;

    @NotNull(groups = {onCreate.class})
    @NameValidation(groups = {onCreate.class, onUpdate.class}, required = true, maxLength = 100)
    private String productName;

    @NotNull(groups = {onCreate.class})
    private MultipartFile[] productImages;

    @NameValidation(groups = {onCreate.class, onUpdate.class}, required = true, minLength = 300, maxLength = 1000)
    private String productDescription;

    @NumberValidation(groups = {onCreate.class, onUpdate.class}, min = 1)
    private int coat;

    @NotNull(groups = {onCreate.class})
    @Size(groups = {onCreate.class}, min=5)
    @Pattern(groups = {onCreate.class}, regexp = "^(\\d+\\s?(min|mins|hr|hrs))$")
    private String dryingTime;

    @NotNull(groups = {onCreate.class})
    @DecimalNumberValidation(groups = {onCreate.class, onUpdate.class}, min = 0.1F)
    private float coverage;

    @NotNull(groups = {onCreate.class})
    private Brands brand;

    @NotNull(groups = {onCreate.class})
    private Finish finish;

    @NotNull(groups = {onCreate.class})
    @NotEmpty(groups = {onCreate.class})
    private Set<Surface> surfaces;

    @NotNull(groups = {onCreate.class})
    @NotEmpty(groups = {onCreate.class})
    private Set<Position> positions;

    @NotNull(groups = {onCreate.class})
    private ProductType productType;

    @NotNull(groups = {onCreate.class})
    private RoomType roomType;

//    @NotNull(groups = {onCreate.class})
//    @NotEmpty(groups = {onCreate.class})
//    @DataExistingListValidation(groups = {onCreate.class}, service = VariationService.class, method = "")
//    private Set<Integer> variations;

    @Valid
    @NotNull(groups = {onCreate.class})
    @NotEmpty(groups = {onCreate.class})
    private Set<@Valid ProductFeaturesDto> productFeatures;
}
