package hueHarmony.web.annotation.validations;

import hueHarmony.web.component.validation.NameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameValidation {
    String message() default "Invalid input entered.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int minLength() default 5;
    int maxLength() default 50;
    boolean required() default false;
    String name() default "Name";
}
