package com.zivara.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String category;

    private String description;

    // Main thumbnail image
    @Column(nullable = false)
    private String image;

    // Multiple images — alag table mein store hongi
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> images;

    // Stock kitna hai
    @Column(nullable = false)
    private Integer stock = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private String subcategory;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
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

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(Double price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setImage(String image) { this.image = image; }
    public void setImages(List<String> images) { this.images = images; }
    public void setStock(Integer stock) { this.stock = stock; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setSubcategory(String subcategory) { this.subcategory = subcategory; }
}