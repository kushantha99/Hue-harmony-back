//package hueHarmony.web.component.validation;
//
//
//import hueHarmony.web.annotation.validations.QuantityValidation;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//import java.lang.annotation.Annotation;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class QuantityValidator implements ConstraintValidator<QuantityValidation, OrderVariationDto>, Annotation {
//
//    private final VariationService variationService;
//
//    @Override
//    public void initialize(QuantityValidation constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
//    }
//
//    @Override
//    public boolean isValid(OrderVariationDto orderVariationDto, ConstraintValidatorContext constraintValidatorContext) {
//        try{
//            if(orderVariationDto!=null) {
//                if(orderVariationDto.isBackendOrder()){
//                    List<BackendOrderType> orderTypes = switch (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().toList().get(0).getAuthority()) {
//                        case "ROLE_ADMIN" ->
//                                new ArrayList<>(Arrays.asList(BackendOrderType.ALLOWED_ALL, BackendOrderType.ALLOWED_MANAGER, BackendOrderType.ALLOWED_ADMIN));
//                        case "ROLE_MANAGER" ->
//                                new ArrayList<>(Arrays.asList(BackendOrderType.ALLOWED_ALL, BackendOrderType.ALLOWED_MANAGER));
//                        case "ROLE_USER" -> new ArrayList<>(BackendOrderType.ALLOWED_ALL.ordinal());
//                        default -> null;
//                    };
//
//                    if(!variationService.checkVariationBackendOrderPermission(orderVariationDto.getVariationId(), orderTypes)){
//                        addConstraintViolation(constraintValidatorContext, "Variation with id "+orderVariationDto.getVariationId()+" hasn't not allowed for backend orders.");
//                        return false;
//                    }
//                }else if(variationService.checkVariationAvailableStocks(orderVariationDto.getVariationId(), orderVariationDto.getQuantity())){
//                    addConstraintViolation(constraintValidatorContext, "Variation with id "+orderVariationDto.getVariationId()+" hasn't enough stocks available.");
//                    return false;
//                }
//            }
//            return true;
//        } catch (Exception e) {
//            throw new RuntimeException("Validation method invocation failed", e);
//        }
//    }
//
//    @Override
//    public Class<? extends Annotation> annotationType() {
//        return null;
//    }
//
//    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
//        context.disableDefaultConstraintViolation();
//        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
//    }
//}
