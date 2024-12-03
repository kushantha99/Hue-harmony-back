package hueHarmony.web.dto;

import hueHarmony.web.annotation.validations.DataExistingValidation;
import hueHarmony.web.annotation.validations.NameValidation;
import hueHarmony.web.service.CustomerService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    public interface onUpdate{}
    public interface onCreation{}
    public interface onOldCreation{}

//    @NotNull(groups = {onOldCreation.class, onUpdate.class}, message = "No customer found.")
//    @DataExistingValidation(groups = {onOldCreation.class, onUpdate.class}, service = CustomerService.class, method = "isCustomerExist")
//    private int customerId;

    @NotNull(groups = {onCreation.class, onUpdate.class}, message = "First name cannot be empty.")
    @NameValidation(groups = {onCreation.class, onUpdate.class}, name = "First name", required = true)
    private String firstName;

    @NotNull(groups = {onCreation.class, onUpdate.class}, message = "Last name cannot be empty.")
    @NameValidation(groups = {onCreation.class, onUpdate.class}, name = "Last name", required = true)
    private String lastName;

    @NotNull(groups = {onCreation.class, onUpdate.class}, message = "Contact number cannot be empty.")
    @NameValidation(groups = {onCreation.class, onUpdate.class}, name = "Contact number", required = true, minLength = 10, maxLength = 12, message = "Contact number must be in between 10 and 12 digits.")
    @Pattern(groups = {onCreation.class, onUpdate.class}, regexp = "^(07[0-9]{8}|0[1-9][0-9]{8}|\\+947[0-9]{8}|\\+94[1-9][0-9]{8})$", message = "Invalid contact number provided.")
    private Set<String> contactNos;

    private String email;
}
