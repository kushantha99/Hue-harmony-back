package hueHarmony.web.annotation.validations;

import hueHarmony.web.component.validation.ContentPermissionListValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ContentPermissionListValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentPermissionListValidation {
    String message() default "Invalid list items";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<?> service();
    String method();
    String key() default "";
    boolean haltOnError() default false;
}
