package hueHarmony.web.controller;

import hueHarmony.web.dto.response_dto.CartItemDto;
import hueHarmony.web.service.CartService;
import hueHarmony.web.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("cart")
public class Cart {


    private final CartService cartService;

    // Get all items in the user's cart
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemDto>> getCartItems(@PathVariable("userId") int userId) {
        List<CartItemDto> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }


    @PostMapping("/{userId}")
    public ResponseEntity<CartItemDto> addCartItem(@PathVariable Long userId, @RequestBody CartItemDto cartItemDto) {
        CartItemDto addedCartItem = cartService.addCartItem(userId, cartItemDto);
        return new ResponseEntity<>(addedCartItem, HttpStatus.CREATED);
    }

    // Update quantity of an item in the cart
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<CartItemDto> updateCartItemQuantity(@PathVariable("cartItemId") int cartItemId, @RequestParam("quantity") int quantity) {
        try{
            CartItemDto updatedItem = cartService.updateCartItemQuantity(cartItemId, quantity);
            return ResponseEntity.ok(updatedItem);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    // Remove item from the cart
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable("cartItemId") int cartItemId) {
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
