package com.zivara.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateProfileRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String phone;
    private String gender;
    private String dob;

    // Getters
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getGender() { return gender; }
    public String getDob() { return dob; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setGender(String gender) { this.gender = gender; }
    public void setDob(String dob) { this.dob = dob; }
}
