//package hueHarmony.web.component.validation;
//
//import hueHarmony.web.annotation.validations.UniqueCategoryValidation;
//import hueHarmony.web.service.CategoryService;
//import hueHarmony.web.service.SubCategoryService;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.lang.annotation.Annotation;
//
//@Component
//public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategoryValidation, String>, Annotation {
//
//    private final CategoryService categoryService;
//    private final SubCategoryService subCategoryService;
//    private String categoryType;
//
//    @Autowired
//    public UniqueCategoryValidator(CategoryService categoryService, SubCategoryService subCategoryService){
//        this.categoryService = categoryService;
//        this.subCategoryService = subCategoryService;
//    }
//
//    @Override
//    public void initialize(UniqueCategoryValidation constraintAnnotation) {
//        this.categoryType = constraintAnnotation.categoryType();
//        ConstraintValidator.super.initialize(constraintAnnotation);
//    }
//
//    @Override
//    public boolean isValid(String category, ConstraintValidatorContext constraintValidatorContext) {
//        if(categoryService.isCategoryNameExists(category)){
//            addConstraintViolation(constraintValidatorContext, categoryType.equals("Category")?categoryType+" name is already exists.":categoryType+" name is already exists as sub category name.");
//            return false;
//        }
//        if(subCategoryService.isSubCategoryNameExists(category)){
//            addConstraintViolation(constraintValidatorContext, categoryType.equals("Sub category")?categoryType+" name is already exists.":categoryType+" name is already exists as category name.");
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
