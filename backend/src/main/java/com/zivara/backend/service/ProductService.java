package com.zivara.backend.service;

import com.zivara.backend.dto.ProductRequest;
import com.zivara.backend.dto.ProductResponse;
import com.zivara.backend.model.Product;
import com.zivara.backend.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Saare products
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Ek product by ID
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return toResponse(product);
    }

    // Category se products
    public List<ProductResponse> getProductsByCategory(String category) {
        return productRepository.findByCategory(category)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Search by name
    public List<ProductResponse> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getLatestProducts(int limit){
        return productRepository
                .findAllByOrderByCreatedAtDesc(PageRequest.of(0, limit))
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getPriceDropProducts(){
//        999 se kam wale products - sale me hai
        return productRepository
                .findByPriceLessThanEqualOrderByPriceAsc(999.0)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Naya product banao — ADMIN only
    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setDescription(request.getDescription());
        product.setImage(request.getImage());
        product.setImages(request.getImages());
        product.setStock(request.getStock());
        product.setSubcategory(request.getSubcategory());

        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    // Product update karo — ADMIN only
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setDescription(request.getDescription());
        product.setImage(request.getImage());
        product.setImages(request.getImages());
        product.setStock(request.getStock());
        product.setSubcategory(request.getSubcategory());

        return toResponse(productRepository.save(product));
    }

    // Product delete karo — ADMIN only
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    public List<ProductResponse> getProductsByCategoryAndSubcategory(String category, String subcategory) {
        return productRepository.findByCategoryAndSubcategory(category, subcategory)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Product → ProductResponse convert karo
    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getSubcategory(),
                product.getDescription(),
                product.getImage(),
                product.getImages(),
                product.getStock(),
                product.getCreatedAt()
        );
    }
}