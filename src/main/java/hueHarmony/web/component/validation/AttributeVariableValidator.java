package hueHarmony.web.component.validation;

import hueHarmony.web.annotation.validations.AttributeVariableValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;

public class AttributeVariableValidator implements ConstraintValidator<AttributeVariableValidation, String>, Annotation {

    private int min;
    private int max;

    @Override
    public void initialize(AttributeVariableValidation constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if(name.length()<min){
            addConstraintViolation(constraintValidatorContext, " hasn't 2 characters.");
            return false;
        }
        if(name.length()>max){
            addConstraintViolation(constraintValidatorContext, " has more than "+max+" characters.");
            return false;
        }
        if(!name.matches("^([a-zA-Z0-9/]+|\\s?[a-zA-Z0-9/]+)$")) {
            addConstraintViolation(constraintValidatorContext, " has special character.");
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
