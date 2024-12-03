package hueHarmony.web.annotation.validations;

import hueHarmony.web.component.validation.PasswordMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordMatchValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatchValidation {
    String message() default "Invalid input entered.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
