package hueHarmony.web.component.validation;

import hueHarmony.web.annotation.validations.NameValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
@NoArgsConstructor
public class NameValidator implements ConstraintValidator<NameValidation, String>, Annotation {

    private int minLength;
    private int maxLength;
    private boolean required;
    private String name;

    @Override
    public void initialize(NameValidation constraintAnnotation) {
        minLength = constraintAnnotation.minLength();
        maxLength = constraintAnnotation.maxLength();
        required = constraintAnnotation.required();
        name = constraintAnnotation.name();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String attributeName, ConstraintValidatorContext constraintValidatorContext) {
        if(required && (attributeName==null || attributeName.isEmpty())){
            addConstraintViolation(constraintValidatorContext, name+" cannot be empty.");
            return false;
        }
        if(attributeName!=null && attributeName.isEmpty()) {
            attributeName = attributeName.trim();
            if (attributeName.length() < minLength || attributeName.length() > maxLength) {
                addConstraintViolation(constraintValidatorContext, name + " must be contain " + minLength + " and " + maxLength + " characters.");
                return false;
            }
            //        if(!name.isEmpty() && ){
            //            addConstraintViolation(constraintValidatorContext, name+" must be contain "+minLength+" and "+maxLength+" characters.");
            //            return false;
            //        }
        }
        return true;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
