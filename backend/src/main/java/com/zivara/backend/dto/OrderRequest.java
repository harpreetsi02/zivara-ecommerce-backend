package com.zivara.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class OrderRequest {

    @NotEmpty(message = "Order must have at least one item")
    private List<OrderItemRequest> items;

    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    // Getters
    public List<OrderItemRequest> getItems() { return items; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public String getPaymentMethod() { return paymentMethod; }

    // Setters
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}