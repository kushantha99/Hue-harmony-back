package hueHarmony.web.component.validation;

import hueHarmony.web.util.JwtUtil;
import hueHarmony.web.util.TypeUtils;
import hueHarmony.web.annotation.validations.ContentPermissionListValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ContentPermissionListValidator implements ConstraintValidator<ContentPermissionListValidation, List<?>>, Annotation {

    private final ApplicationContext applicationContext;
    private final JwtUtil jwtUtil;
    private final TypeUtils typeUtils;
    private final ValidationMethodCache validationMethodCache;

//    @Autowired
//    public ContentPermissionListValidator(JwtUtil jwtUtil, ApplicationContext applicationContext){
//        this.applicationContext = applicationContext;
//        this.jwtUtil = jwtUtil;
//    }

    private Class<?> serviceClass;
    private String method;
    private String key;
    private boolean haltOnError;

    @Override
    public void initialize(ContentPermissionListValidation constraintAnnotation) {
        this.serviceClass = constraintAnnotation.service();
        this.method = constraintAnnotation.method();
        this.haltOnError = constraintAnnotation.haltOnError();
        this.key = constraintAnnotation.key().isEmpty() ?
                String.valueOf(jwtUtil.extractUserId(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString())) :
                constraintAnnotation.key();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<?> objects, ConstraintValidatorContext constraintValidatorContext) {
        try{
            if(objects!=null && serviceClass!=null && method!=null && key!=null) {
                int objectCount = objects.size();
                StringBuilder message = new StringBuilder();
                for(Object object : objects) {
                    Object service = applicationContext.getBean(this.serviceClass);
                    Object[] args = {object, key};
                    if(
                        !(boolean) validationMethodCache.getCachedMethod(serviceClass, method, args).invoke(service, object, key)
                    ) {
                        objects.remove(object);
                        if (haltOnError) {
                            addConstraintViolation(constraintValidatorContext, "User has no permission for "+object+".");
                            return false;
                        }else{
                            message.append("|").append("User has no permission for ").append(object).append(".");
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
