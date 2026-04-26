package com.zivara.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ProductResponse {

    private Long id;
    private String name;
    private Double price;
    private String category;
    private String description;
    private String image;
    private List<String> images;
    private Integer stock;
    private LocalDateTime createdAt;
    private String subcategory;

    public ProductResponse(Long id, String name, Double price, String category,
                           String subcategory, String description, String image,
                           List<String> images, Integer stock, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
        this.images = images;
        this.stock = stock;
        this.createdAt = createdAt;
        this.subcategory = subcategory;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public Double getPrice() { return price; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getImage() { return image; }
    public List<String> getImages() { return images; }
    public Integer getStock() { return stock; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getSubcategory() { return subcategory; }
}