package hueHarmony.web.dto;

import hueHarmony.web.annotation.validations.ContentPermissionValidation;
import hueHarmony.web.annotation.validations.DataExistingValidation;
import hueHarmony.web.annotation.validations.DecimalNumberValidation;
import hueHarmony.web.service.SupplierService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierVariationDto {

    public interface onCreation{}
    public interface onUpdate{}
    public interface onDelete{}

    @NotNull(groups = {onUpdate.class, onDelete.class}, message = "Supplier variation cannot be found.")
    @DataExistingValidation(groups = {onUpdate.class, onDelete.class}, service = SupplierService.class, method = "isSupplierVariationExist")
    private int supplierVariationId;

    @NotNull(groups = {onCreation.class, onUpdate.class}, message = "Unit cost cannot empty.")
    @DecimalNumberValidation(groups = {onUpdate.class, onDelete.class}, name = "Unit cost", min = 0.1F, max =100000F)
    private float unitCost;

    @NotNull(groups = {onCreation.class, onUpdate.class}, message = "Discount cannot empty.")
    @DecimalNumberValidation(groups = {onUpdate.class, onDelete.class}, name = "Discount", min = 0.1F, max =100000F)
    private float discount;

    @NotNull(groups = {onCreation.class}, message = "Supplier cannot be found.")
    @DataExistingValidation(groups = {onCreation.class}, service = SupplierService.class, method = "isSupplierExist")
    @ContentPermissionValidation(groups = {onCreation.class}, service = SupplierService.class, method = "checkSupplierStatus", key = "ACTIVE")
    private int supplier;

//    @NotNull(groups = {onCreation.class}, message = "Variation cannot be found.")
//    @DataExistingValidation(groups = {onCreation.class}, service = VariationService.class, method = "isVariationExist")
//    private int variation;
}
