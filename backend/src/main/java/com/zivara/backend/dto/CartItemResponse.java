package com.zivara.backend.dto;

public class CartItemResponse {

    private Long id;
    private Long productId;
    private String productName;
    private String productImage;
    private Double productPrice;
    private Integer quantity;
    private String size;
    private Double subtotal;

    public CartItemResponse(Long id, Long productId, String productName,
                            String productImage, Double productPrice,
                            Integer quantity, String size) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.size = size;
        this.subtotal = productPrice * quantity;
    }

    // Getters
    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getProductImage() { return productImage; }
    public Double getProductPrice() { return productPrice; }
    public Integer getQuantity() { return quantity; }
    public String getSize() { return size; }
    public Double getSubtotal() { return subtotal; }
}