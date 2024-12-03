package hueHarmony.web.dto;

import hueHarmony.web.model.enums.data_set.CustomerCategory;
import hueHarmony.web.model.enums.data_set.CustomerSortOptions;
import hueHarmony.web.model.enums.data_set.SupplierCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterSupplierDto {

    public interface whenOrganization{}

    private String search;

    private SupplierCategory select = SupplierCategory.NAME;

    @Min(groups = {FilterVariationDto.whenOrganization.class}, value = 1)
    private int limit;

    @Min(groups = {FilterVariationDto.whenOrganization.class}, value = 1)
    private int page;
}
