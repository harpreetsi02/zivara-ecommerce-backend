package com.zivara.backend.repository;

import com.zivara.backend.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Category se products dhundo
    List<Product> findByCategory(String category);

    // Name mein search karo — case insensitive
    List<Product> findByNameContainingIgnoreCase(String name);

    // Price range se filter karo
    List<Product> findByPriceBetween(Double min, Double max);

    List<Product> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Product> findByPriceLessThanEqualOrderByPriceAsc(Double price);

    List<Product> findByCategoryAndSubcategory(String category, String subcategory);
}