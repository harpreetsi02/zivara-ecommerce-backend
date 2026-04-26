package com.zivara.backend.controller;

import com.zivara.backend.dto.WishlistResponse;
import com.zivara.backend.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    // GET /api/wishlist
    @GetMapping
    public ResponseEntity<List<WishlistResponse>> getWishlist(
            @AuthenticationPrincipal String email) {
        return ResponseEntity.ok(wishlistService.getWishlist(email));
    }

    // POST /api/wishlist/{productId}/toggle
    @PostMapping("/{productId}/toggle")
    public ResponseEntity<Map<String, String>> toggle(
            @AuthenticationPrincipal String email,
            @PathVariable Long productId) {
        String result = wishlistService.toggle(email, productId);
        return ResponseEntity.ok(Map.of("action", result));
    }

    // GET /api/wishlist/{productId}/check
    @GetMapping("/{productId}/check")
    public ResponseEntity<Map<String, Boolean>> check(
            @AuthenticationPrincipal String email,
            @PathVariable Long productId) {
        boolean wishlisted = wishlistService.isWishlisted(email, productId);
        return ResponseEntity.ok(Map.of("wishlisted", wishlisted));
    }
}