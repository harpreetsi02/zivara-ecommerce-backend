package com.zivara.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public class ProductRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotBlank(message = "Category is required")
    private String category;

    private String description;

    @NotBlank(message = "Image is required")
    private String image;

    private List<String> images;

    @NotNull(message = "Stock is required")
    private Integer stock;

    private String subcategory;

    // Getters
    public String getName() { return name; }
    public Double getPrice() { return price; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getImage() { return image; }
    public List<String> getImages() { return images; }
    public Integer getStock() { return stock; }
    public String getSubcategory() { return subcategory; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setPrice(Double price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setImage(String image) { this.image = image; }
    public void setImages(List<String> images) { this.images = images; }
    public void setStock(Integer stock) { this.stock = stock; }
    public void setSubcategory(String subcategory) { this.subcategory = subcategory; }
}