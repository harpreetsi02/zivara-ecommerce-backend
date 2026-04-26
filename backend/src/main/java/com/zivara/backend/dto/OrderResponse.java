package com.zivara.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private Long id;
    private String status;
    private Double totalAmount;
    private String deliveryAddress;
    private String paymentMethod;
    private List<OrderItemResponse> items;
    private LocalDateTime createdAt;

    public OrderResponse(Long id, String status, Double totalAmount,
                         String deliveryAddress, String paymentMethod,
                         List<OrderItemResponse> items, LocalDateTime createdAt) {
        this.id = id;
        this.status = status;
        this.totalAmount = totalAmount;
        this.deliveryAddress = deliveryAddress;
        this.paymentMethod = paymentMethod;
        this.items = items;
        this.createdAt = createdAt;
    }

    // Getters
    public Long getId() { return id; }
    public String getStatus() { return status; }
    public Double getTotalAmount() { return totalAmount; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public String getPaymentMethod() { return paymentMethod; }
    public List<OrderItemResponse> getItems() { return items; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}