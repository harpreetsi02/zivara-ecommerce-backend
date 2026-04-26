package com.zivara.backend.repository;

import com.zivara.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring khud SQL likhega:
    // SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);

    // SELECT COUNT(*) FROM users WHERE email = ?
    boolean existsByEmail(String email);
}