package hueHarmony.web.repository;

import hueHarmony.web.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

//    @Query("SELECT NEW hueHarmony.web.dto.response_dto.CartItemDto(" +
//                "ci.cartItemId," +
//                "ci.product.productId" +
//                "ci.quantity" +
//                "ci.variation.variationImage," +
//                "CASE WHEN ci.variation.discount=0 THEN ci.variation.unitPrice*ci.quantity ELSE ci.variation.discount*ci.quantity END," +
//                "ci.variation.unitPrice," +
//                "ci.product.productTitle" +
//            ") " +
//            "FROM CartItem ci " +
//            "WHERE ci.user.userId = :userId")
//    List<CartItemDto> findAllCartItemsByUserId(@Param("userId") long userId);

//    boolean existsByCartItemId(int id);
//
//    @Modifying
//    @Query("UPDATE CartItem ci SET ci.quantity = :qty WHERE ci.cartItemId = :cartItemId")
//    void updateCartItemQuantityByCartItemId(@Param("cartItemId") Long cartItemId, @Param("qty") int qty);
//
//    @Query("SELECT CASE WHEN ci.user.userId = :userId THEN TRUE ELSE FALSE END FROM CartItem ci WHERE ci.cartItemId = :cartItemId")
//    boolean checkPermissionForCartOfUserByUserId(@Param("cartItemId") Long cartItemId, @Param("userId") int userId);
//
//    @Query("SELECT ci.product.productId FROM CartItem ci WHERE ci.cartItemId = :cartItemId")
//    int getProductIdByCartItemId(@Param("cartItemId") Long cartItem);
    @Query("SELECT ci FROM CartItem ci WHERE ci.user.userId=:userId")
    List<CartItem> findByUserId(@Param("userId") int userId);

    long getProductIdByCartItemId(long cartItemId);
}
