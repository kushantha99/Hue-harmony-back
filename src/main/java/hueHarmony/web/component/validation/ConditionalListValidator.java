package hueHarmony.web.component.validation;


import hueHarmony.web.annotation.validations.ConditionalListValidation;
import jakarta.validation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class ConditionalListValidator implements ConstraintValidator<ConditionalListValidation, List<?>> {

    private Validator validator;
    private boolean required;
    private String name;

    @Override
    public void initialize(ConditionalListValidation constraintAnnotation) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        this.required = constraintAnnotation.required();
        this.name = constraintAnnotation.name();
    }

    @Override
    public boolean isValid(List<?> items, ConstraintValidatorContext context) {
        if ((items == null || items.isEmpty()) && required){
            addConstraintViolation(context, name+" cannot be empty.");
            return false;
        }

        List<Map<String, String>> errors = new ArrayList<>();

        for (Object item : items) {
            Set<ConstraintViolation<Object>> violations = validator.validate(item);
            if (!violations.isEmpty()) {
                for (ConstraintViolation<Object> violation : violations) {
                    Map<String, String> properties = new HashMap<>();
                    properties.put("message", violation.getMessage());
                    properties.put("propertyPath", violation.getPropertyPath().toString()+"|"+extractIdentifier(item));
                    errors.add(properties);

                }
            }
        }

        if (!errors.isEmpty()) {
            context.disableDefaultConstraintViolation();
            for (Map<String, String> error : errors) {
                context.buildConstraintViolationWithTemplate(error.get("message"))
                        .addPropertyNode(error.get("propertyPath"))
                        .addConstraintViolation();
            }
            return false;
        }

        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

    private String extractIdentifier(Object item) {
        try {
            Field field = item.getClass().getDeclaredField("identifier");
            field.setAccessible(true);
            return field.get(item).toString();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return "unknown";
        }
    }
}

