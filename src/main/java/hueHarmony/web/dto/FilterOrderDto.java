package hueHarmony.web.dto;

import hueHarmony.web.annotation.validations.ContentPermissionValidation;
import hueHarmony.web.model.enums.data_set.OrderCategory;
import hueHarmony.web.model.enums.data_set.SortOptions;
import hueHarmony.web.service.OrderService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterOrderDto {

    public interface whenUser{}
    public interface whenOrganization{}

    @Size(groups = {whenUser.class}, max = 0)
    private String search;

    @NotNull(groups = {whenOrganization.class})
    private OrderCategory select = OrderCategory.ORDER_NUMBER;

    @NotNull(groups = {whenUser.class, whenOrganization.class})
    private SortOptions sort = SortOptions.NEWEST;

    @Min(groups = {whenUser.class, whenOrganization.class}, value = 1)
    private int limit;

    @Min(groups = {whenUser.class, whenOrganization.class}, value = 1)
    private int page;

    @NotNull(groups = {whenUser.class})
    @Min(groups = {whenUser.class}, value = 1)
    @ContentPermissionValidation(groups = {whenUser.class},  service = OrderService.class, method = "")
    private int userId;
}
