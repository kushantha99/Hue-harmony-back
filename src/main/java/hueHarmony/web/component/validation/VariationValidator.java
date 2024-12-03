//package hueHarmony.web.component.validation;
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//import java.lang.annotation.Annotation;
//
//public class VariationValidator implements ConstraintValidator<VariationValidation, VariationUpsertDto>, Annotation {
//    @Override
//    public void initialize(VariationValidation constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
//    }
//
//    @Override
//    public boolean isValid(VariationUpsertDto variationDto, ConstraintValidatorContext constraintValidatorContext) {
//
//        if(variationDto.getDiscount()>variationDto.getUnitPrice()){
//            addConstraintViolation(constraintValidatorContext, "Discount cannot be higher than the unit price.");
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public Class<? extends Annotation> annotationType() {
//        return null;
//    }
//
//    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
//        context.disableDefaultConstraintViolation();
//        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
//    }
//}
