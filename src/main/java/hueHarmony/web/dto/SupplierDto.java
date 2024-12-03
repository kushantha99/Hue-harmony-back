package hueHarmony.web.dto;


import hueHarmony.web.annotation.validations.DataExistingValidation;
import hueHarmony.web.annotation.validations.NameValidation;
import hueHarmony.web.model.enums.SupplierStatus;
import hueHarmony.web.model.enums.SupplierType;
import hueHarmony.web.service.SupplierService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {

    public interface onCreation{}
    public interface onUpdate{}
    public interface onDelete{}
    public interface onStatusUpdate{}

    @NotNull(groups = {onUpdate.class, onDelete.class, onStatusUpdate.class}, message = "Supplier not found.")
    @DataExistingValidation(groups = {onUpdate.class, onDelete.class, onStatusUpdate.class}, service = SupplierService.class, method = "isSupplierExist")
    private int supplierId;

    @NotNull(groups = {onCreation.class, onUpdate.class}, message = "Supplier name cannot be empty.")
    @NameValidation(groups = {onCreation.class, onUpdate.class}, required = true, maxLength = 100, name = "Supplier name")
    private String supplierName;

    @NotNull(groups = {onCreation.class, onUpdate.class}, message = "Supplier address cannot be empty.")
    @Size(groups = {onCreation.class, onUpdate.class}, min = 5, max = 150, message = "Supplier address must have 5 to 150 characters.")
    private String supplierAddress;

    @Size(groups = {onCreation.class, onUpdate.class}, max = 15, message = "Supplier mobile phone number cannot have more than 15 digits.")
    private String supplierMobilePhone;

    @NotNull(groups = {onCreation.class, onUpdate.class}, message = "Supplier email cannot be empty.")
    @Size(groups = {onCreation.class, onUpdate.class}, min = 10, max = 100, message = "Supplier email must have 10 to 100 characters.")
    @Email(groups = {onCreation.class, onUpdate.class}, message = "Invalid email address provided.")
    private String supplierEmail;

    @NotNull(groups = {onCreation.class, onUpdate.class}, message = "Supplier land phone number cannot be empty.")
    @Size(groups = {onCreation.class, onUpdate.class}, min = 10, max = 15, message = "Supplier land phone number must have 10 to 15 digits.")
    private String supplierLandPhone;

    @NotNull(groups = {onCreation.class, onUpdate.class}, message = "Supplier type cannot be empty.")
    private SupplierType supplierType = SupplierType.LOCAL;

    @NotNull(groups = {onStatusUpdate.class}, message = "Supplier status cannot be empty.")
    private SupplierStatus supplierStatus;

    private List<SupplierProductDto>supplierProduct;

}
