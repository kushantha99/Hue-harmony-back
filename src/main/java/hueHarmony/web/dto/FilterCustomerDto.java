package hueHarmony.web.dto;

import hueHarmony.web.model.enums.data_set.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterCustomerDto {

    public interface whenOrganization{}

    private String search;

    private CustomerCategory select = CustomerCategory.CUSTOMER_NAME;

    @NotNull(groups = {FilterVariationDto.whenOrganization.class})
    private CustomerSortOptions sort = CustomerSortOptions.NEWEST;

    @Min(groups = {FilterVariationDto.whenOrganization.class}, value = 1)
    private int limit;

    @Min(groups = {FilterVariationDto.whenOrganization.class}, value = 1)
    private int page;
}
