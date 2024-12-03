//package hueHarmony.web.annotation.validations;
//
//import hueHarmony.web.component.validation.VariationValidator;
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//@Constraint(validatedBy = VariationValidator.class)
//@Target({ ElementType.TYPE })
//@Retention(RetentionPolicy.RUNTIME)
//public @interface VariationValidation {
//
//    String message() default "Invalid list items";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//}
