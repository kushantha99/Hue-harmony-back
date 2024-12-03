package hueHarmony.web.component.validation;

import hueHarmony.web.annotation.validations.DecimalNumberValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;

public class DecimalNumberValidator implements ConstraintValidator<DecimalNumberValidation, Float>, Annotation {

    private float min;
    private float max;
    private String name;

    @Override
    public void initialize(DecimalNumberValidation constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.name = constraintAnnotation.name();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Float number, ConstraintValidatorContext constraintValidatorContext) {
        if(min>number && (max==0.0F || max<number)){
            addConstraintViolation(constraintValidatorContext, (max==0.0F) ? name+" must be "+min+" or more." : name+" must be between "+min+" and "+max+".");
            return false;
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
