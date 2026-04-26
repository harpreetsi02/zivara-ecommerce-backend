package com.zivara.backend.dto;

import java.time.LocalDateTime;

public class WishlistResponse {

    private Long id;
    private Long productId;
    private String productName;
    private String productImage;
    private Double productPrice;
    private String category;
    private LocalDateTime addedAt;

    public WishlistResponse(Long id, Long productId, String productName,
                            String productImage, Double productPrice,
                            String category, LocalDateTime addedAt) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.category = category;
        this.addedAt = addedAt;
    }

    // Getters
    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getProductImage() { return productImage; }
    public Double getProductPrice() { return productPrice; }
    public String getCategory() { return category; }
    public LocalDateTime getAddedAt() { return addedAt; }
}