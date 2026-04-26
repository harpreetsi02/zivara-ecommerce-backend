package com.zivara.backend.dto;

public class ProfileResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String gender;
    private String dob;
    private String role;

    public ProfileResponse(Long id, String name, String email,
                           String phone, String gender,
                           String dob, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.role = role;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getGender() { return gender; }
    public String getDob() { return dob; }
    public String getRole() { return role; }
}