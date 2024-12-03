package hueHarmony.web.dto;

import hueHarmony.web.model.enums.data_set.UserCategory;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterUserDto {

    public interface whenOrganization{}

    private String search;

    private UserCategory select = UserCategory.NAME;

    @Min(groups = {FilterVariationDto.whenOrganization.class}, value = 1)
    private int limit;

    @Min(groups = {FilterVariationDto.whenOrganization.class}, value = 1)
    private int page;
}
