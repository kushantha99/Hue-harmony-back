package hueHarmony.web.component.validation;

import hueHarmony.web.annotation.validations.ContentPermissionValidation;
import hueHarmony.web.util.JwtUtil;
import hueHarmony.web.util.TypeUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
@RequiredArgsConstructor
public class ContentPermissionValidator implements ConstraintValidator<ContentPermissionValidation, Object>, Annotation {

    private final ApplicationContext applicationContext;
    private final JwtUtil jwtUtil;
    private final TypeUtils typeUtils;
    private final ValidationMethodCache validationMethodCache;

    private Class<?> serviceClass;
    private String method;
    private String key;
    private String message;

    @Override
    public void initialize(ContentPermissionValidation constraintAnnotation) {
        this.serviceClass = constraintAnnotation.service();
        this.method = constraintAnnotation.method();
        this.key = constraintAnnotation.key().isEmpty() ?
                String.valueOf(jwtUtil.extractUserId(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString())) :
                constraintAnnotation.key();
        this.message = constraintAnnotation.message();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try{
            if(value!=null && serviceClass!=null && method!=null && key!=null) {
                Object service = applicationContext.getBean(this.serviceClass);
                Object[] args = {value, key};
                if(
                    !(boolean) validationMethodCache.getCachedMethod(serviceClass, method, args).invoke(service, value, key)
                ){
                    if(!message.isEmpty()) addConstraintViolation(constraintValidatorContext, value+" is not exists.");
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
