package hueHarmony.web.component.validation;

import hueHarmony.web.annotation.validations.PasswordMatchValidation;
import hueHarmony.web.dto.ChangePasswordDto;
import hueHarmony.web.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
@RequiredArgsConstructor
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatchValidation, ChangePasswordDto>, Annotation {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void initialize(PasswordMatchValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ChangePasswordDto value, ConstraintValidatorContext context) {
        if(value.getOldPassword().equals(value.getNewPassword())){
            addConstraintViolation(context, "Old and new passwords do not match.");
            return false;
        }

        if(!userService.isPasswordSame(passwordEncoder.encode(value.getOldPassword()), value.getUserId())){
            addConstraintViolation(context, "Old passwords do not match.");
            return false;
        }

        return true;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
