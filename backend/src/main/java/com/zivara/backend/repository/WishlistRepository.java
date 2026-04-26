package com.zivara.backend.repository;

import com.zivara.backend.model.User;
import com.zivara.backend.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByUser(User user);

    Optional<WishlistItem> findByUserAndProductId(User user, Long productId);

    boolean existsByUserAndProductId(User user, Long productId);
}