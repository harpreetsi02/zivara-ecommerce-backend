package com.zivara.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kisne order kiya
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Order ke items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private Double totalAmount;

    private String deliveryAddress;

    private String paymentMethod;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = OrderStatus.PENDING;
        }
    }

    // Getters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public List<OrderItem> getItems() { return items; }
    public OrderStatus getStatus() { return status; }
    public Double getTotalAmount() { return totalAmount; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public String getPaymentMethod() { return paymentMethod; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}