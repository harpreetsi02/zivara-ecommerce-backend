package com.zivara.backend.service;

import com.zivara.backend.dto.*;
import com.zivara.backend.model.*;
import com.zivara.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        UserRepository userRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // Order banao
    @Transactional
    public OrderResponse createOrder(String email, OrderRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Pehle total calculate karo
        double total = 0;
        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemReq.getProductId()));
            total += product.getPrice() * itemReq.getQuantity();
        }

        // Ab order banao — total ke saath
        Order order = new Order();
        order.setUser(user);
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(total); // pehle set karo

        // Ab save karo
        Order savedOrder = orderRepository.save(order);

        // Items process karo
        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Stock check karo
            if (product.getStock() < itemReq.getQuantity()) {
                throw new RuntimeException("Insufficient stock for: " + product.getName());
            }

            // Stock kam karo
            product.setStock(product.getStock() - itemReq.getQuantity());
            productRepository.save(product);

            // OrderItem banao
            OrderItem item = new OrderItem();
            item.setOrder(savedOrder);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(product.getPrice());
            item.setSize(itemReq.getSize());
            orderItemRepository.save(item);
        }

        return toResponse(savedOrder);
    }

    // User ke saare orders
    public List<OrderResponse> getUserOrders(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Ek order by ID
    public OrderResponse getOrderById(Long orderId, String email) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Check karo ki yeh order usi user ka hai
        if (!order.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        return toResponse(order);
    }

    // Order cancel karo
    @Transactional
    public OrderResponse cancelOrder(Long orderId, String email) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Delivered order cannot be cancelled");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order already cancelled");
        }

        // Stock wapas karo
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus(OrderStatus.CANCELLED);
        return toResponse(orderRepository.save(order));
    }

    // ADMIN — status update karo
    public OrderResponse updateStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        return toResponse(orderRepository.save(order));
    }

    // Order → OrderResponse
    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems() == null
                ? List.of()
                : order.getItems().stream().map(item ->
                new OrderItemResponse(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getImage(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getSize()
                )
        ).collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                order.getTotalAmount(),
                order.getDeliveryAddress(),
                order.getPaymentMethod(),
                itemResponses,
                order.getCreatedAt()
        );
    }
}