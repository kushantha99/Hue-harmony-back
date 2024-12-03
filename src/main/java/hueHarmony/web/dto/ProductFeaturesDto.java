package hueHarmony.web.dto;

import hueHarmony.web.annotation.validations.DataExistingValidation;
import hueHarmony.web.annotation.validations.NameValidation;
import hueHarmony.web.service.ProductService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFeaturesDto {

    public interface onCreate{}
    public interface onAdd{}

    @NotNull(groups = {onAdd.class})
    @DataExistingValidation(groups = {onAdd.class}, service = ProductService.class, method = "")
    private int productFeatureId;

    @NotNull(groups = {onCreate.class})
    @NameValidation(groups = {onCreate.class}, required = true)
    private String featureName;
}
