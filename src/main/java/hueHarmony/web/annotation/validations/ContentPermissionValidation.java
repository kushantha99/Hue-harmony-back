package hueHarmony.web.annotation.validations;

import hueHarmony.web.component.validation.ContentPermissionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ContentPermissionValidator.class)
@Target({ ElementType.FIELD, ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentPermissionValidation {
    String message() default "Invalid list items";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<?> service();
    String method();
    String key() default "";
}
