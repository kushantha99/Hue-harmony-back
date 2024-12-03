package hueHarmony.web.dto;

import hueHarmony.web.annotation.validations.DecimalNumberValidation;
import hueHarmony.web.model.enums.data_set.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterVariationDto {

    public interface whenOrganization{}

    private String search;

    private VariationCategory select = VariationCategory.VARIATION_BARCODE;

    private List<Brands> brands;

    private List<Size> sizes;

    @DecimalNumberValidation(max = 100000F)
    private float startPrice;

    @DecimalNumberValidation(max = 100000F)
    private float endPrice;

    @NotNull(groups = {FilterVariationDto.whenOrganization.class})
    private VariationSortOptions sort = VariationSortOptions.NEWEST;

    @Min(groups = {FilterVariationDto.whenOrganization.class}, value = 1)
    private int limit;

    @Min(groups = {FilterVariationDto.whenOrganization.class}, value = 1)
    private int page;
}
