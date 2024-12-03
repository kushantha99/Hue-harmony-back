package hueHarmony.web.dto;

import hueHarmony.web.annotation.validations.ContentPermissionValidation;
import hueHarmony.web.annotation.validations.DataExistingValidation;
import hueHarmony.web.annotation.validations.NameValidation;
import hueHarmony.web.model.enums.WholeSaleCustomerStatus;
import hueHarmony.web.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ContentPermissionValidation(groups = {WholeSaleCustomerDto.onUpdate.class}, service = CustomerService.class, method = "isValidCustomerId", key = "No Key", message = "Invalid data provided.")
public class WholeSaleCustomerDto {

    public interface onCreation{}
    public interface onUpdate{}
    public interface onDelete{}
    public interface onStatusUpdate{}

//    @NotNull(groups = {onDelete.class, onUpdate.class, onStatusUpdate.class}, message = "No wholesale customer found.")
//    @DataExistingValidation(groups = {onDelete.class, onUpdate.class, onStatusUpdate.class}, service = CustomerService.class, method = "isWholeSaleCustomerExist")
//    private int wholeSaleCustomerId;

    @NotNull(groups = {onCreation.class, onUpdate.class}, message = "NIC number cannot be empty.")
    @NameValidation(groups = {onCreation.class, onUpdate.class}, name = "NIC number", required = true, minLength = 10, maxLength = 12)
    @Pattern(groups = {onCreation.class, onUpdate.class}, regexp = "^\\d{9}[VXvx]|\\d{12}$", message = "NIC must be either 9 digits followed by V or X, or 12 digits")
    private String nicNo;

    @NotNull(groups = {onCreation.class, onUpdate.class}, message = "Address cannot be empty.")
    @NameValidation(groups = {onCreation.class, onUpdate.class}, name = "Address", required = true, minLength = 10, maxLength = 150)
    private String address;

    @NotNull(groups = {onCreation.class, onUpdate.class}, message = "Land phone number cannot be empty.")
    @NameValidation(groups = {onCreation.class, onUpdate.class}, name = "Land phone number", required = true, minLength = 10, maxLength = 12, message = "Land phone number must be in between 10 and 12 digits.")
    @Pattern(groups = {onCreation.class, onUpdate.class}, regexp = "^(0[1-9][0-9]{8}|\\+94[1-9][0-9]{8})$", message = "Invalid land phone number provided.")
    private String landPhone;

//    @NotNull(groups = {onStatusUpdate.class}, message = "Wholesale customer status cannot be empty.")
//    private WholeSaleCustomerStatus wholeSaleCustomerStatus;

    @Valid
    @NotNull(groups = {onCreation.class, onUpdate.class}, message = "Customer details cannot be empty.")
    private CustomerDto customerDto;


    private String deliveryAddress;

    private String businessAddress;

    private String contactPerson;

    private String businessName;

    private Integer contactPersonNumber;
}
