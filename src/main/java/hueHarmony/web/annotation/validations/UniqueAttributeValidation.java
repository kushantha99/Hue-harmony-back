//package hueHarmony.web.annotation.validations;
//
//import hueHarmony.web.component.validation.UniqueAttributeValidator;
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//
//import java.lang.annotation.*;
//
//@Documented
//@Constraint(validatedBy = UniqueAttributeValidator.class)
//@Target({ElementType.FIELD, ElementType.PARAMETER})
//@Retention(RetentionPolicy.RUNTIME)
//public @interface UniqueAttributeValidation {
//    String message() default "Category already exists.";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//}
