package hueHarmony.web.component.validation;

import hueHarmony.web.annotation.validations.PasswordValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
@NoArgsConstructor(force = true)
public class PasswordValidator implements ConstraintValidator<PasswordValidation, String>, Annotation {

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

    @Override
    public void initialize(PasswordValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if(password==null || password.isEmpty()){
            addConstraintViolation(constraintValidatorContext, "Password cannot be empty.");
            return false;
        }
        password = password.trim();

        if(password.length()<8 || password.length()>12){
            addConstraintViolation(constraintValidatorContext, "Password must be contain between 8 and 12 characters.");
            return false;
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$")) {
            addConstraintViolation(constraintValidatorContext, "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character");
            return false;
        }
        return true;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
