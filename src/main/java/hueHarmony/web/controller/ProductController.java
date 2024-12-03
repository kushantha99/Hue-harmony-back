//package hueHarmony.web.controller;
//
//import com.fasterxml.jackson.annotation.JsonView;
//import hueHarmony.web.dto.FilterProductDto;
//import hueHarmony.web.dto.ProductDto;
//import hueHarmony.web.dto.response.ProductDisplayDto;
//import hueHarmony.web.model.Product;
//import hueHarmony.web.service.FirebaseStorageService;
//import hueHarmony.web.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("product")
//public class ProductController {
//
//    private final ProductService productService;
//    private final FirebaseStorageService firebaseStorageService;
//
//
//    @Autowired
//    public ProductController(ProductService productService,FirebaseStorageService firebaseStorageService) {
//        this.productService = productService;
//        this.firebaseStorageService=firebaseStorageService;
//    }
//
////    @GetMapping()
////    public ResponseEntity<?> getAllProducts(@ModelAttribute FilterProductDto filterProductDto) {
////        Page<ProductDto> products = productService.getAllProducts();
////
////        if (products.isEmpty()) {
////            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No products available");
////        }
////
////        return ResponseEntity.ok(products);
////        //return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No products available");
////    }
//
////    @PostMapping("/create")
////    public ResponseEntity<?> createProduct(@RequestBody  @Validated(ProductDto.onCreate.class) ProductDto productDto, BindingResult bindingResult) throws IOException {
////        if (bindingResult.hasErrors()) {
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
////        }
////
////
////        Product newProduct = Product.builder()
////                .productName(productDto.getProductName())
////                .productDescription(productDto.getProductDescription())
////                .productImageUrl(firebaseStorageService.uploadFile(productDto.getProductImage().getName(),productDto.getProductImage().getBytes(),productDto.getProductImage().getContentType()))
////                .brand(productDto.getProductBrand())
////                .dryingTime(productDto.getDryingTime())
////                /* .roomType(productDto.getRoomType())*/
////                .productStatus(productDto.getProductStatus())
////                /*.productFeatures(productDto.getProductFeatures())*/
////                /*.applicableSurfaces(productDto.getSurfaces())*/
////                .positions(productDto.getPositions())
////                .build();
////
////        Product savedProduct=productService.save(newProduct);
////
////
////        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
////        /*return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No products available");*/
////    }
//
//    @GetMapping("view/{id}")
//    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
//        Product product =productService.getProductById(id);
//            if (product == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
//            }else{
//                ProductDto productDto = ProductDto.builder()
//                        .productName(product.getProductName())
////                        .startingPrice(product.getProductPrice())
//                        .productStatus(product.getProductStatus())
//                        .build();
//                return ResponseEntity.ok(productDto);
//            }
//    }
//
////    @GetMapping("/search")
////    public ResponseEntity<?> search(@RequestParam String category, @RequestParam String key, @RequestParam("page")int page, @RequestParam("limit")int limit) {
////
////        Page<ProductDisplayDto> products = productService.searchProducts(category, key, page, limit);
////
////
////        return ResponseEntity.ok("Sucess");
////    }
//
//
//
//}
