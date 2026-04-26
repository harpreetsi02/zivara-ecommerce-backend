package com.zivara.backend.dto;

public class OrderItemResponse {

    private Long id;
    private Long productId;
    private String productName;
    private String productImage;
    private Integer quantity;
    private Double price;
    private String size;

    public OrderItemResponse(Long id, Long productId, String productName,
                             String productImage, Integer quantity,
                             Double price, String size) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
    }

    // Getters
    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getProductImage() { return productImage; }
    public Integer getQuantity() { return quantity; }
    public Double getPrice() { return price; }
    public String getSize() { return size; }
}