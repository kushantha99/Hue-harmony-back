//package hueHarmony.web.annotation.validations;
//
//import hueHarmony.web.component.validation.UniqueCategoryValidator;
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//
//import java.lang.annotation.*;
//
//@Documented
//@Constraint(validatedBy = UniqueCategoryValidator.class)
//@Target({ElementType.FIELD, ElementType.PARAMETER})
//@Retention(RetentionPolicy.RUNTIME)
//public @interface UniqueCategoryValidation {
//    String message() default "Category already exists.";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//
//    String categoryType() default "Category";
//}
