//package hueHarmony.web.component.validation;
//
//import hueHarmony.web.annotation.validations.UniqueAttributeValidation;
//import hueHarmony.web.service.AttributeService;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.lang.annotation.Annotation;
//
//@Component
//@NoArgsConstructor(force = true)
//public class UniqueAttributeValidator implements ConstraintValidator<UniqueAttributeValidation, String>, Annotation {
//
//    private final AttributeService attributeService;
//
//    @Autowired
//    public  UniqueAttributeValidator(AttributeService attributeService){
//        this.attributeService = attributeService;
//    }
//
//    @Override
//    public void initialize(UniqueAttributeValidation constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
//    }
//
//    @Override
//    public boolean isValid(String attribute, ConstraintValidatorContext constraintValidatorContext) {
//        if(attributeService.isAttributeExists(attribute)){
//            addConstraintViolation(constraintValidatorContext, attribute+" attribute is already exists.");
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
