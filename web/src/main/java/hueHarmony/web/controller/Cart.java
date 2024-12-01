package hueHarmony.web.controller;

import hueHarmony.web.dto.response_dto.CartItemDto;
import hueHarmony.web.service.CartService;
import hueHarmony.web.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
public class Cart {

    @Autowired
    private CartService cartService;

    // Get all items in the user's cart
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemDto>> getCartItems(@PathVariable("userId") int userId) {
        List<CartItemDto> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }

    // Add item to the cart
    @PostMapping("/add")
    public ResponseEntity<CartItemDto> addCartItem(@RequestBody CartItemDto cartItemDto) {
        CartItemDto newItem = cartService.addCartItem(cartItemDto);
        return ResponseEntity.ok(newItem);
    }

    // Update quantity of an item in the cart
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<CartItemDto> updateCartItemQuantity(@PathVariable("cartItemId") int cartItemId, @RequestParam("quantity") int quantity) {
        CartItemDto updatedItem = cartService.updateCartItemQuantity(cartItemId, quantity);
        return ResponseEntity.ok(updatedItem);
    }

    // Remove item from the cart
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable("cartItemId") int cartItemId) {
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }
}
