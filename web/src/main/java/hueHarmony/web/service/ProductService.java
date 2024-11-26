package hueHarmony.web.service;

import hueHarmony.web.dto.ProductDto;
import hueHarmony.web.model.Product;
import hueHarmony.web.repository.ProductRepository;
import hueHarmony.web.specification.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final FirebaseStorageService firebaseStorageService;

    public List<ProductDto> getAllProducts() {
        return productRepository.findAllProductListDto();
    }

    public Product save(Product newProduct) {
        return productRepository.save(newProduct);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> searchProducts(String category, String key) {
        Specification<Product> specification = Specification.where(ProductSpecifications.withKeyword(key))
                .and(ProductSpecifications.withCategory(category));

        return productRepository.findAll(specification);
    }

    public Page<ProductDisplayDto> filterProductsForDashboardTable(FilterProductDto productFilterDto){
        Specification<Product> productSpecification = Specification
                .where(ProductSpecification.hasName(productFilterDto.getSearch()))
                .and(ProductSpecification.hasProductStatus(productFilterDto.getStatus()));
//                .and(ProductSpecification.betweenDates(productFilterDto.getStartDate(), productFilterDto.getEndDate()));

        return productRepository.filterAndSelectFieldsBySpecsAndPage(
                productSpecification,
                PageRequest.of(productFilterDto.getPage(), productFilterDto.getLimit()).withSort(productFilterDto.getSortOrder(), productFilterDto.getSortCol()),
                List.of("productId", "productTitle", "productStatus", "productImage","productPrice"),
                ProductDisplayDto.class
        ).map(product -> {
                    ProductDisplayDto dto = (ProductDisplayDto) product;
                    dto.setProductImage(firebaseStorageService.getFileDownloadUrl(dto.getProductImage(), 60, TimeUnit.MINUTES));
                    return dto;
                }
        );
    }

    public Page<ProductUserDisplayDto> filterProductsForList(FilterProductDto productFilterDto){
        Float[] unitPriceRange = ConvertUtil.convertRangeToArray(
                productFilterDto.getSellingPrice(),
                Float::parseFloat,
                new Float[0]
        );

        Specification<Product> productSpecification = Specification
                .where(ProductSpecification.hasProductStatus(Collections.singleton(ProductStatus.APPROVED)))
                .and(ProductSpecification.betweenVariationUnitPrice(unitPriceRange[0], unitPriceRange[1]));

        Sort.Direction direction;
        String column;

        switch (productFilterDto.getSort()){
            case LOWEST -> {
                direction = Sort.Direction.ASC;
                column = "unitPrice";
            }
            case HIGHEST -> {
                direction = Sort.Direction.DESC;
                column = "unitPrice";
            }
            default -> {
                direction = Sort.Direction.DESC;
                column = "productPublishedTime";
            }
        }

        return productRepository.filterAndSelectFieldsBySpecsAndPage(
                productSpecification,
                PageRequest.of(productFilterDto.getPage(), productFilterDto.getLimit()).withSort(direction, column),
                List.of("productId", "productTitle", "productStatus, productImage", "reviewCount", "productRate"),
                ProductUserDisplayDto.class
        ).map(product -> {
            ProductUserDisplayDto dto = (ProductUserDisplayDto) product;
            return new ProductUserDisplayDto(
                    dto.getProductId(),
                    dto.getProductTitle(),
                    dto.getProductStatus(),
                    firebaseStorageService.getFileDownloadUrl(dto.getProductImage(), 60, TimeUnit.MINUTES),
                    dto.getPrice(),
                    dto.isSale(),
                    dto.isNew(),
                    dto.getReviewRate(),
                    dto.getTotalReviews(),
                    dto.getDiscount()

            );
        });
    }


    public void createProduct(AddProductDto addProductDto) {
        Product product = new Product();

        // Basic fields
        product.setProductName(addProductDto.getProductName());
        product.setProductDescription(addProductDto.getProductDescription());
        product.setProductPrice(addProductDto.getProductPrice());
        product.setProductDiscount(addProductDto.getProductDiscount());
        product.setCoat(addProductDto.getCoat());
        product.setDryingTime(addProductDto.getDryingTime() + " hours");
        product.setCoverage(addProductDto.getCoverage());

        product.setProductStatus(ProductStatus.valueOf(addProductDto.getProductStatus().toUpperCase()));
        product.setBrand(addProductDto.getBrand());
        product.setRoomType(addProductDto.getRoomType());
        product.setFinish(addProductDto.getFinish());

        List<Surface> validSurfaces = addProductDto.getSurfaces().stream()
                .filter(Surface::contains)
                .map(value -> Surface.valueOf(value.toUpperCase()))
                .toList();
        product.setSurfaces(validSurfaces);

        List<Position> validPositions = addProductDto.getPositions().stream()
                .filter(Position::contains)
                .map(value -> Position.valueOf(value.toUpperCase()))
                .toList();
        product.setPositions(validPositions);

        List<ProductType> validProductTypes = addProductDto.getProductTypes().stream()
                .filter(ProductType::contains)
                .map(value -> ProductType.valueOf(value.toUpperCase()))
                .toList();
        product.setProductType(validProductTypes);

        product.setProductFeatures(addProductDto.getProductFeatures());

        List<String> imageIds = firebaseStorageService.uploadImagesToFirebase(addProductDto.getProductImage());

        product.setImageIds(imageIds);

        System.out.println(product);

        productRepository.save(product);

    }


    public void deleteProduct(Long productId) throws Exception {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new Exception("Product not found");
        }
        productRepository.delete(product.get());
    }

}
