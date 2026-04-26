package com.zivara.backend.service;

import com.zivara.backend.dto.WishlistResponse;
import com.zivara.backend.model.*;
import com.zivara.backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // Wishlist fetch karo
    public List<WishlistResponse> getWishlist(String email) {
        User user = getUser(email);
        return wishlistRepository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Toggle — already hai toh remove, nahi hai toh add
    public String toggle(String email, Long productId) {
        User user = getUser(email);

        if (wishlistRepository.existsByUserAndProductId(user, productId)) {
            WishlistItem item = wishlistRepository
                    .findByUserAndProductId(user, productId)
                    .orElseThrow();
            wishlistRepository.delete(item);
            return "removed";
        } else {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            WishlistItem item = new WishlistItem();
            item.setUser(user);
            item.setProduct(product);
            wishlistRepository.save(item);
            return "added";
        }
    }

    // Check karo ki product wishlist mein hai ya nahi
    public boolean isWishlisted(String email, Long productId) {
        User user = getUser(email);
        return wishlistRepository.existsByUserAndProductId(user, productId);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private WishlistResponse toResponse(WishlistItem item) {
        return new WishlistResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getImage(),
                item.getProduct().getPrice(),
                item.getProduct().getCategory(),
                item.getAddedAt()
        );
    }
}