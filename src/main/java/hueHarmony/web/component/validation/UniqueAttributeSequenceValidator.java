//package hueHarmony.web.component.validation;
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//import java.lang.annotation.Annotation;
//import java.util.HashSet;
//import java.util.List;
//
//public class UniqueAttributeSequenceValidator implements ConstraintValidator<UniqueAttributeSequenceValidation, List<VariationUpsertDto>>, Annotation {
//    @Override
//    public void initialize(UniqueAttributeSequenceValidation constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
//    }
//
//    @Override
//    public boolean isValid(List<VariationUpsertDto> variationUpsertDtos, ConstraintValidatorContext constraintValidatorContext) {
//        if (variationUpsertDtos == null) {
//            return true; // Consider null as valid to let @NotNull handle it
//        }
//        if(new HashSet<>(variationUpsertDtos.stream().map(dto->dto.getVariationAttributes().stream().map(AttributeVariablesDto::getAttributeId).toList().stream().sorted().toList()).toList()).size()!=1){
//            addConstraintViolation(constraintValidatorContext, "Variations have different attributes.");
//            return false;
//        }
//        return true;
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
