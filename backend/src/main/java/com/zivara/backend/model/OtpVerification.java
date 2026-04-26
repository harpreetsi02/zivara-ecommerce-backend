package com.zivara.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp_verifications")
public class OtpVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email; // phone ki jagah email

    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private boolean verified;

    @PrePersist
    public void prePersist() {
        this.verified = false;
    }

    // Getters
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getOtp() { return otp; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public boolean isVerified() { return verified; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setOtp(String otp) { this.otp = otp; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public void setVerified(boolean verified) { this.verified = verified; }
}