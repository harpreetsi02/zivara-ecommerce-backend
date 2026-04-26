package com.zivara.backend.controller;

import com.zivara.backend.dto.OrderRequest;
import com.zivara.backend.dto.OrderResponse;
import com.zivara.backend.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // POST /api/orders — naya order
    // @AuthenticationPrincipal — JWT filter ne jo email set ki thi woh milegi
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @AuthenticationPrincipal String email,
            @Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(email, request));
    }

    // GET /api/orders — user ke saare orders
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getUserOrders(
            @AuthenticationPrincipal String email) {
        return ResponseEntity.ok(orderService.getUserOrders(email));
    }

    // GET /api/orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @AuthenticationPrincipal String email,
            @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id, email));
    }

    // PUT /api/orders/{id}/cancel
    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(
            @AuthenticationPrincipal String email,
            @PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id, email));
    }

    // PUT /api/orders/{id}/status — ADMIN only
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateStatus(id, status));
    }
}