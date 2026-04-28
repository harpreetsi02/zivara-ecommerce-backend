package com.zivara.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zivara.backend.dto.OrderResponse;
import com.zivara.backend.dto.ProfileResponse;
import com.zivara.backend.model.Order;
import com.zivara.backend.model.User;
import com.zivara.backend.repository.OrderRepository;
import com.zivara.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public AdminController(OrderRepository orderRepository,
                           UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // GET /api/admin/orders — saare orders 
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> responses = orders.stream()
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getStatus().name(),
                        order.getTotalAmount(),
                        order.getDeliveryAddress(),
                        order.getPaymentMethod(),
                        order.getItems() == null ? List.of() :
                                order.getItems().stream().map(item ->
                                        new com.zivara.backend.dto.OrderItemResponse(
                                                item.getId(),
                                                item.getProduct().getId(),
                                                item.getProduct().getName(),
                                                item.getProduct().getImage(),
                                                item.getQuantity(),
                                                item.getPrice(),
                                                item.getSize()
                                        )
                                ).collect(Collectors.toList()),
                        order.getCreatedAt()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // GET /api/admin/users — saare users
    @GetMapping("/users")
    public ResponseEntity<List<ProfileResponse>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<ProfileResponse> responses = users.stream()
                .map(u -> new ProfileResponse(
                        u.getId(),
                        u.getName(),
                        u.getEmail(),
                        u.getPhone(),
                        u.getGender(),
                        u.getDob(),
                        u.getRole().name()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}