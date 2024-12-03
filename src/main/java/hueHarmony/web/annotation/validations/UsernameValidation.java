package hueHarmony.web.annotation.validations;

import hueHarmony.web.component.validation.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameValidation {
    String message() default "Invalid input entered.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

