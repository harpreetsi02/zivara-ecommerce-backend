package com.zivara.backend.repository;

import com.zivara.backend.model.Order;
import com.zivara.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // User ke saare orders
    List<Order> findByUserOrderByCreatedAtDesc(User user);
}