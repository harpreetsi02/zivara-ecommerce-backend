package com.zivara.backend.controller;

import com.zivara.backend.dto.CartItemRequest;
import com.zivara.backend.dto.CartResponse;
import com.zivara.backend.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // GET /api/cart
    @GetMapping
    public ResponseEntity<CartResponse> getCart(
            @AuthenticationPrincipal String email) {
        return ResponseEntity.ok(cartService.getCart(email));
    }

    // POST /api/cart
    @PostMapping
    public ResponseEntity<CartResponse> addToCart(
            @AuthenticationPrincipal String email,
            @Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addToCart(email, request));
    }

    // PUT /api/cart/{itemId}
    @PutMapping("/{itemId}")
    public ResponseEntity<CartResponse> updateQuantity(
            @AuthenticationPrincipal String email,
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateQuantity(email, itemId, quantity));
    }

    // DELETE /api/cart/{itemId}
    @DeleteMapping("/{itemId}")
    public ResponseEntity<CartResponse> removeItem(
            @AuthenticationPrincipal String email,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeFromCart(email, itemId));
    }

    // DELETE /api/cart — clear cart
    @DeleteMapping
    public ResponseEntity<Void> clearCart(
            @AuthenticationPrincipal String email) {
        cartService.clearCart(email);
        return ResponseEntity.noContent().build();
    }
}