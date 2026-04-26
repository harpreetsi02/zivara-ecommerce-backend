package com.zivara.backend.repository;

import com.zivara.backend.model.CartItem;
import com.zivara.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser(User user);

    Optional<CartItem> findByUserAndProductIdAndSize(User user, Long productId, String size);

    void deleteByUser(User user);
}