//package hueHarmony.web.component.validation;
//
//import hueHarmony.web.annotation.validations.DuplicateSubCategoryValidation;
//import hueHarmony.web.model.SubCategory;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import lombok.NoArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.lang.annotation.Annotation;
//
//@Component
//@NoArgsConstructor
//public class DuplicateSubCategoryValidator implements ConstraintValidator<DuplicateSubCategoryValidation, List<SubCategory>>, Annotation {
//    @Override
//    public void initialize(DuplicateSubCategoryValidation constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
//    }
//
//    @Override
//    public boolean isValid(List<SubCategory> subCategories, ConstraintValidatorContext constraintValidatorContext) {
//
//        return false;
//    }
//
//    @Override
//    public Class<? extends Annotation> annotationType() {
//        return null;
//    }
//}
