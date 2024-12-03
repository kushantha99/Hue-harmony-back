package hueHarmony.web.annotation.validations;

import hueHarmony.web.component.validation.NumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NumberValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberValidation {

    String message() default "Invalid input entered.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int min() default 0;
    int max() default 0;
    String name() default "Name";
}
