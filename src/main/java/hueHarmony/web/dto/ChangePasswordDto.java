package hueHarmony.web.dto;

import hueHarmony.web.annotation.validations.ContentPermissionValidation;
import hueHarmony.web.annotation.validations.DataExistingValidation;
import hueHarmony.web.annotation.validations.PasswordMatchValidation;
import hueHarmony.web.annotation.validations.PasswordValidation;
import hueHarmony.web.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatchValidation(groups = {ChangePasswordDto.onCheck.class})
public class ChangePasswordDto {

    public interface onCheck{}

    @NotNull(groups = {onCheck.class}, message = "User cannot be found.")
    @DataExistingValidation(groups = {onCheck.class}, service = UserService.class, method = "isUserExist")
    @ContentPermissionValidation(groups = {onCheck.class}, service = UserService.class, method = "isUserHavePermission", message = "Unauthorized to access.")
    private int userId;

    @PasswordValidation(groups = {onCheck.class})
    private String oldPassword;

    @PasswordValidation(groups = {onCheck.class})
    private String newPassword;
}
