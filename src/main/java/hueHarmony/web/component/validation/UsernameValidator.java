package hueHarmony.web.component.validation;

import hueHarmony.web.annotation.validations.UsernameValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
@NoArgsConstructor(force = true)
public class UsernameValidator implements ConstraintValidator<UsernameValidation, String>, Annotation {

    @Override
    public void initialize(UsernameValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {

        if(username==null || username.isEmpty()){
            addConstraintViolation(constraintValidatorContext, "Username cannot be empty.");
            return false;
        }
        username = username.trim();

        if (username.length() < 3 || username.length() > 20) {
            addConstraintViolation(constraintValidatorContext, "Username must be between 3 and 20 characters.");
            return false;
        }
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            addConstraintViolation(constraintValidatorContext, "Username can only contain letters, numbers, and underscore.");
            return false;
        }

//        if (userService.isUsernameExists(username)) {
//            addConstraintViolation(constraintValidatorContext, "Username is already exists.");
//            return false;
//        }
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
