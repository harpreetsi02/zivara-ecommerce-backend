package com.zivara.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderItemRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    private String size;

    // Getters
    public Long getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public String getSize() { return size; }

    // Setters
    public void setProductId(Long productId) { this.productId = productId; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setSize(String size) { this.size = size; }
}