package com.zivara.backend.controller;

import com.zivara.backend.dto.ProductRequest;
import com.zivara.backend.dto.ProductResponse;
import com.zivara.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/products — saare products
    // GET /api/products?search=dress — search
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "0") int limit) {

        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(productService.searchProducts(search));
        }
        if (limit > 0) {
            return ResponseEntity.ok(productService.getLatestProducts(limit));
        }
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/price-drop")
    public ResponseEntity<List<ProductResponse>> getPriceDropProducts(){
        return ResponseEntity.ok(productService.getPriceDropProducts());
    }

    // GET /api/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // GET /api/products/category/{slug}
//    @GetMapping("/category/{slug}")
//    public ResponseEntity<List<ProductResponse>> getByCategory(@PathVariable String slug) {
//        return ResponseEntity.ok(productService.getProductsByCategory(slug));
//    }

    // POST /api/products — ADMIN only
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(request));
    }

    // PUT /api/products/{id} — ADMIN only
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    // DELETE /api/products/{id} — ADMIN only
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/products/category/dresses?subcategory=bodycon
    @GetMapping("/category/{slug}")
    public ResponseEntity<List<ProductResponse>> getByCategory(
            @PathVariable String slug,
            @RequestParam(required = false) String subcategory) {
        if (subcategory != null && !subcategory.isBlank()) {
            return ResponseEntity.ok(productService.getProductsByCategoryAndSubcategory(slug, subcategory));
        }
        return ResponseEntity.ok(productService.getProductsByCategory(slug));
    }
}