package hueHarmony.web.component.validation;

import hueHarmony.web.annotation.validations.UniqueEmailValidation;
import hueHarmony.web.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
@NoArgsConstructor(force = true)
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmailValidation, String>, Annotation {

    private final UserService userService;

    @Autowired
    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UniqueEmailValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.isEmailExists(email);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
