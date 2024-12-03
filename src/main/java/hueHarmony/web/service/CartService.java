package hueHarmony.web.service;

import hueHarmony.web.dto.response_dto.CartItemDto;
import hueHarmony.web.model.CartItem;
import hueHarmony.web.model.User;
import hueHarmony.web.model.Product;
import hueHarmony.web.repository.CartItemRepository;
import hueHarmony.web.repository.UserRepository;
import hueHarmony.web.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FirebaseStorageService storageService;

    // Get all cart items for a specific user
    public List<CartItemDto> getCartItems(int userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        return cartItems.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Add an item to the cart
    public CartItemDto addCartItem(Long userId, CartItemDto cartItemDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById((long) cartItemDto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemDto.getQuantity());

        cartItem = cartItemRepository.save(cartItem);
        return convertToDto(cartItem);
    }

    // Update the quantity of an item in the cart
    public CartItemDto updateCartItemQuantity(int cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById((long) cartItemId).orElseThrow(() -> new RuntimeException("CartItem not found"));
        cartItem.setQuantity(quantity);
        cartItem = cartItemRepository.save(cartItem);
        return convertToDto(cartItem);
    }

    // Remove an item from the cart
    public void removeCartItem(int cartItemId) {
        CartItem cartItem = cartItemRepository.findById((long) cartItemId).orElseThrow(() -> new RuntimeException("CartItem not found"));
        cartItemRepository.delete(cartItem);
    }

    // Helper method to convert CartItem to CartItemDto
    private CartItemDto convertToDto(CartItem cartItem) {
        CartItemDto dto = new CartItemDto();
        dto.setCartItemId(cartItem.getCartItemId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setItemImage(storageService.getFileDownloadUrl(cartItem.getProduct().getImageIds().get(0),60, TimeUnit.MINUTES));
        dto.setPrice(cartItem.getProduct().getProductPrice());
        dto.setFullPrice(cartItem.getProduct().getProductPrice() * cartItem.getQuantity());
        dto.setProductName(cartItem.getProduct().getProductName());
        return dto;
    }
}
