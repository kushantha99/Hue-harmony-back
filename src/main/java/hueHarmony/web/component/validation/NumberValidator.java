package hueHarmony.web.component.validation;

import hueHarmony.web.annotation.validations.NumberValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class NumberValidator implements ConstraintValidator<NumberValidation, Integer>, Annotation {

    private int min;
    private int max;
    private String name;
    @Override
    public void initialize(NumberValidation constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.name = constraintAnnotation.name();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer number, ConstraintValidatorContext constraintValidatorContext) {
        if(min>number && (max==0 || max<number)){
            addConstraintViolation(constraintValidatorContext, (max==0) ? name+" must be "+min+" or more." : name+" must be between "+min+" and "+max+".");
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
