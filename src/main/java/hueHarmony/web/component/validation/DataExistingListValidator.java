package hueHarmony.web.component.validation;

import hueHarmony.web.annotation.validations.DataExistingListValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataExistingListValidator implements ConstraintValidator<DataExistingListValidation, List<?>>, Annotation {

    private final ApplicationContext applicationContext;
    private final ValidationMethodCache validationMethodCache;

    private Class<?> serviceClass;
    private String method;
    private boolean haltOnError;

    @Override
    public void initialize(DataExistingListValidation constraintAnnotation) {
        this.serviceClass = constraintAnnotation.service();
        this.method = constraintAnnotation.method();
        this.haltOnError = constraintAnnotation.haltOnError();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<?> objects, ConstraintValidatorContext constraintValidatorContext) {
        try{
            if(objects==null || objects.isEmpty()) return true;

            if(serviceClass!=null && method!=null) {
                int objectCount = objects.size();
                StringBuilder message = new StringBuilder();
                Object service = applicationContext.getBean(this.serviceClass);
                for(Object object : objects) {
                    Object[] args = {object};
                    if(!(boolean) validationMethodCache.getCachedMethod(serviceClass, method, args).invoke(service, object)){
                        if(haltOnError){
                            addConstraintViolation(constraintValidatorContext, object+" is not exists.");
                            return false;
                        }else{
                            message.append("|").append(object).append(" is not exists.");
                        }
                    }
                }
                if(!message.isEmpty()) addConstraintViolation(constraintValidatorContext, message.toString());
                return objects.size()==objectCount;
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Validation method invocation failed", e);
        }
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
