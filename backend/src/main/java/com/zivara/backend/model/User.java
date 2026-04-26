package com.zivara.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity                          // yeh class ek database table hai
@Table(name = "users")           // table ka naam "users" hoga
public class User {

    @Id                                        // yeh primary key hai
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto increment — 1, 2, 3...
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @Email(message = "Enter a valid email")
    @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true)   // har user ka email alag hoga
    private String email;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;                   // hamesha encrypted store hogi

    @Enumerated(EnumType.STRING)              // database mein "USER" ya "ADMIN" store hoga
    @Column(nullable = false)
    private Role role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String phone;
    private String gender;
    private String dob;

    @PrePersist                               // database mein save hone se pehle automatically chalega
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.role == null) {
            this.role = Role.USER;            // default role USER hogi
        }
    }

    // ── GETTERS ──
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getPhone() { return phone; }
    public String getGender() { return gender; }
    public String getDob() { return dob; }

    // ── SETTERS ──
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setGender(String gender) { this.gender = gender; }
    public void setDob(String dob) { this.dob = dob; }
}