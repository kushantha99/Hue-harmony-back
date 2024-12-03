//package hueHarmony.web.annotation.validations;
//
//import hueHarmony.web.component.validation.QuantityValidator;
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//@Constraint(validatedBy = QuantityValidator.class)
//@Target({ ElementType.TYPE }) // Apply at the class level
//@Retention(RetentionPolicy.RUNTIME)
//public @interface QuantityValidation {
//
//    String message() default "";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//}
