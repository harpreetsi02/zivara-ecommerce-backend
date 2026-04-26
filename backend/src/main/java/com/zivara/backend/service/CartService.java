package com.zivara.backend.service;

import com.zivara.backend.dto.*;
import com.zivara.backend.model.*;
import com.zivara.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       UserRepository userRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // Cart fetch karo
    public CartResponse getCart(String email) {
        User user = getUser(email);
        List<CartItem> items = cartRepository.findByUser(user);
        return buildCartResponse(items);
    }

    // Item add karo ya quantity update karo
    public CartResponse addToCart(String email, CartItemRequest request) {
        User user = getUser(email);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Already cart mein hai same size ke saath?
        var existing = cartRepository.findByUserAndProductIdAndSize(
                user, request.getProductId(), request.getSize());

        if (existing.isPresent()) {
            // Quantity badhao
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            cartRepository.save(item);
        } else {
            // Naya item add karo
            CartItem item = new CartItem();
            item.setUser(user);
            item.setProduct(product);
            item.setQuantity(request.getQuantity());
            item.setSize(request.getSize());
            cartRepository.save(item);
        }

        return getCart(email);
    }

    // Quantity update karo
    public CartResponse updateQuantity(String email, Long itemId, Integer quantity) {
        CartItem item = cartRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        // Check karo ki yeh item usi user ka hai
        if (!item.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        if (quantity <= 0) {
            cartRepository.delete(item);
        } else {
            item.setQuantity(quantity);
            cartRepository.save(item);
        }

        return getCart(email);
    }

    // Item remove karo
    public CartResponse removeFromCart(String email, Long itemId) {
        CartItem item = cartRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!item.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        cartRepository.delete(item);
        return getCart(email);
    }

    // Cart clear karo — order place hone ke baad
    @Transactional
    public void clearCart(String email) {
        User user = getUser(email);
        cartRepository.deleteByUser(user);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private CartResponse buildCartResponse(List<CartItem> items) {
        List<CartItemResponse> itemResponses = items.stream()
                .map(item -> new CartItemResponse(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getImage(),
                        item.getProduct().getPrice(),
                        item.getQuantity(),
                        item.getSize()
                ))
                .collect(Collectors.toList());

        double total = itemResponses.stream()
                .mapToDouble(CartItemResponse::getSubtotal)
                .sum();

        int totalItems = itemResponses.stream()
                .mapToInt(CartItemResponse::getQuantity)
                .sum();

        return new CartResponse(itemResponses, total, totalItems);
    }
}