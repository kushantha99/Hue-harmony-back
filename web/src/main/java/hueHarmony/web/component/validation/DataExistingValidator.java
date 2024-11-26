package hueHarmony.web.component.validation;

import hueHarmony.web.annotation.validations.DataExistingValidation;
import hueHarmony.web.util.TypeUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
@RequiredArgsConstructor
public class DataExistingValidator implements ConstraintValidator<DataExistingValidation, Object>, Annotation {

    private final ApplicationContext applicationContext;
    private final TypeUtils typeUtils;
    private final ValidationMethodCache validationMethodCache;

//    @Autowired
//    public DataExistingValidator(ApplicationContext applicationContext){
//        this.applicationContext = applicationContext;
//    }

    private Class<?> serviceClass;
    private String method;

    @Override
    public void initialize(DataExistingValidation constraintAnnotation) {
        this.serviceClass = constraintAnnotation.service();
        this.method = constraintAnnotation.method();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try{
            if(value!=null && serviceClass!=null && method!=null) {
                Object service = applicationContext.getBean(this.serviceClass);
                Object[] args = {value};
                if(!(boolean) validationMethodCache.getCachedMethod(serviceClass, method, args).invoke(service, value)){
                    addConstraintViolation(constraintValidatorContext, value+" is not exists.");
                    return false;
                }
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
