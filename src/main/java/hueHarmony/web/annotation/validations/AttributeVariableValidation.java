package hueHarmony.web.annotation.validations;

import hueHarmony.web.component.validation.AttributeVariableValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AttributeVariableValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AttributeVariableValidation {

    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int min() default 2;

    int max() default 50;
}
