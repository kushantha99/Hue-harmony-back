package hueHarmony.web.annotation.validations;

import hueHarmony.web.component.validation.DecimalNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DecimalNumberValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicateSubCategoryValidation {
    String message() default "Invalid input entered.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
