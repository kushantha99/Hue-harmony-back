package hueHarmony.web.controller;

import hueHarmony.web.dto.ProductVariationDto;
import hueHarmony.web.service.ProductVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product-variant")
public class ProductVariationController {

    private final ProductVariationService productVariationService;

    @Autowired
    public ProductVariationController(ProductVariationService productVariationService) {
        this.productVariationService = productVariationService;
    }

    @PostMapping
    public ResponseEntity<?> createProductVariant(@RequestBody ProductVariationDto productVariationDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productVariationDto);
    }



}
