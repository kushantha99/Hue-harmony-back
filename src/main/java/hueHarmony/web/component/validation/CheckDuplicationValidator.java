package hueHarmony.web.component.validation;

import hueHarmony.web.annotation.validations.CheckDuplicationValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;

public class CheckDuplicationValidator implements ConstraintValidator<CheckDuplicationValidation, List<Object>>, Annotation {
    @Override
    public void initialize(CheckDuplicationValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Object> objects, ConstraintValidatorContext constraintValidatorContext) {
        if (new HashSet<>(objects).size() != objects.size()) {
            addConstraintViolation(constraintValidatorContext, "Duplicate attribute variables found in variation.");
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
