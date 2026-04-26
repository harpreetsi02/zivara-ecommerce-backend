package com.zivara.backend.dto;

import java.util.List;

public class CartResponse {

    private List<CartItemResponse> items;
    private Double totalAmount;
    private Integer totalItems;

    public CartResponse(List<CartItemResponse> items, Double totalAmount, Integer totalItems) {
        this.items = items;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
    }

    // Getters
    public List<CartItemResponse> getItems() { return items; }
    public Double getTotalAmount() { return totalAmount; }
    public Integer getTotalItems() { return totalItems; }
}