package lk.ijse.furnitureapp_back_end.service.impl;

import lk.ijse.furnitureapp_back_end.dto.CartDto;
import lk.ijse.furnitureapp_back_end.dto.CartItemResponseDto;
import lk.ijse.furnitureapp_back_end.dto.CartSaveDto;
import lk.ijse.furnitureapp_back_end.entity.Cart;
import lk.ijse.furnitureapp_back_end.entity.CartItem;
import lk.ijse.furnitureapp_back_end.entity.Product;
import lk.ijse.furnitureapp_back_end.entity.User;
import lk.ijse.furnitureapp_back_end.repo.CartItemRepository;
import lk.ijse.furnitureapp_back_end.repo.CartRepository;
import lk.ijse.furnitureapp_back_end.repo.UserRepository;
import lk.ijse.furnitureapp_back_end.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CartDto saveCart(CartSaveDto cartSaveDto) {
        User user = userRepository.findById(cartSaveDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Cart> existingCart = cartRepository.findByUser(user);
        Cart savedCart;
        if (existingCart.isPresent()) {
            savedCart = existingCart.get();
        } else {
            Cart cart = new Cart();
            cart.setUser(user);
            savedCart = cartRepository.save(cart);
        }

        // Convert DTO items to CartItem entities
        List<CartItem> cartItems = cartSaveDto.getItems().stream().map(dto -> {
            Product product = new Product();
            product.setProductId(dto.getProductId());

            CartItem item = new CartItem();
            item.setCart(savedCart);
            item.setProduct(product);
            item.setQuantity(dto.getQuantity());

            return item;
        }).collect(Collectors.toList());

        // Save all new cart items
        cartItemRepository.saveAll(cartItems);

        // Attach items to the cart for DTO mapping
        savedCart.setCartItems(cartItems);

        return mapToDto(savedCart);
    }



    @Override
    public CartDto getCartByUserId(UUID userId) {
        Cart cart = cartRepository.findByUser_Uid(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user ID: " + userId));
        return mapToDto(cart);
    }

    @Override
    public void clearCartByUserId(UUID userId) {
        Cart cart = cartRepository.findByUser_Uid(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user ID: " + userId));

        cartItemRepository.deleteAll(cart.getCartItems());

        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    private CartDto mapToDto(Cart cart) {
        List<CartItemResponseDto> itemDtos = cart.getCartItems().stream()
                .map(item -> new CartItemResponseDto(
                        item.getProduct().getProductId(),
                        item.getProduct().getName(),
                        item.getProduct().getImageUrl(),
                        item.getQuantity(),
                        item.getProduct().getPrice() * item.getQuantity()
                ))
                .collect(Collectors.toList());

        return new CartDto(
                cart.getCartId(),
                cart.getUser().getUid(),
                itemDtos
        );
    }
}