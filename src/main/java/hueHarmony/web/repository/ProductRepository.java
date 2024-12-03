package hueHarmony.web.repository;

import hueHarmony.web.dto.ProductDto;
import hueHarmony.web.dto.PosProductDto;
import hueHarmony.web.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import hueHarmony.web.dto.PosProductDto;
import hueHarmony.web.dto.response.PopularProductsDto;
import hueHarmony.web.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends ExtendedRepository<Product, Long> {

    List<Product> findByProductName(@Param("productName") String productName);
    @Query("SELECT new hueHarmony.web.dto.PosProductDto(p.productId, p.productName, p.productPrice,p.productDiscount) FROM Product p")
    List<PosProductDto> getProducts();

//    @Query("SELECT new hueHarmony.web.dto.ProductDto(p.productId, p.productName, p.productImageUrl, p.startingPrice, p.productStatus, p.productDiscount) FROM Product p")
//    List<ProductDto> findAllProductListDto();

//    @Query("SELECT new hueHarmony.web.dto.PosProductDto(p.productId, p.productName, p.productPrice,p.productDiscount) FROM Product p")
//    List<PosProductDto> getProducts();
    @Query("SELECT p.productDiscount, p.productPrice FROM Product p WHERE p.productId = :productId")
    List<Object[]> findProductPriceAndDiscountByProductId(@Param("productId")long productId);

    @Query(value = "SELECT p.product_id AS productId, " +
            "p.product_name AS productName, " +
            "STRING_AGG(pi.image_ids, ',') AS imageIds, " +
            "p.product_price AS productPrice, " +
            "p.product_discount AS productDiscount " +
            "FROM Product p " +
            "LEFT JOIN product_image_ids pi ON p.product_id = pi.product_product_id " +
            "GROUP BY p.product_id, p.product_name, p.product_price, p.product_discount",
            nativeQuery = true)
    List<Object[]> findPopularProductsRaw(Pageable pageable);


}
