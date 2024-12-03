package hueHarmony.web.dto;

import hueHarmony.web.annotation.validations.*;
import hueHarmony.web.model.enums.UserStatus;
import hueHarmony.web.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    public interface onSignup{}
    public interface onCreation{}
    public interface onUpdate{}
    public interface onUpdateRole{}
    public interface onUpdateStatus{}

    @NotNull(groups = {onUpdate.class, onUpdateStatus.class, onUpdateRole.class}, message = "User not found.")
    @DataExistingValidation(groups = {onUpdate.class, onUpdateStatus.class, onUpdateRole.class}, service = UserService.class, method = "isUserExist")
    @ContentPermissionValidation(groups = {onUpdate.class}, service = UserService.class, method = "isUserHavePermission", message = "Unauthorized to access.")
    private int userId;

    @PasswordValidation(groups = {onCreation.class, onSignup.class})
    private String password;

    @NotEmpty(message = "Email cannot be empty.", groups = {onCreation.class, onUpdate.class, onSignup.class})
    @Size(groups = {onCreation.class, onUpdate.class, onSignup.class}, min = 10, max = 100, message = "User email must have 10 to 100 characters.")
    @Email(message = "Invalid email entered.", groups = {onCreation.class, onUpdate.class, onSignup.class})
    private String email;

    @UsernameValidation(groups = {onCreation.class, onUpdate.class, onSignup.class})
    private String username;

    @NameValidation(minLength = 10, maxLength = 200, name = "Full name", groups = {onCreation.class, onUpdate.class})
    private String fullName;

    private MultipartFile profileImage;

    @NotNull(groups = {onUpdateStatus.class}, message = "User status cannot be empty.")
    private UserStatus userStatus;

    @NotNull(groups = {onCreation.class, onUpdateRole.class}, message = "No roles found.")
    @NotEmpty(groups = {onCreation.class, onUpdateRole.class}, message = "No roles found.")
    @DataExistingListValidation(groups = {onCreation.class, onUpdateRole.class}, service = UserService.class, method = "isRoleExist", haltOnError = true)
    private Set<Integer> roles;
}
