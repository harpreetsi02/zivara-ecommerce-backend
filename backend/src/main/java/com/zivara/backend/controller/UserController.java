package com.zivara.backend.controller;

import com.zivara.backend.dto.ChangePasswordRequest;
import com.zivara.backend.dto.ProfileResponse;
import com.zivara.backend.dto.UpdateProfileRequest;
import com.zivara.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // GET /api/user/profile
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getProfile(
            @AuthenticationPrincipal String email) {
        return ResponseEntity.ok(userService.getProfile(email));
    }

    // PUT /api/user/profile
    @PutMapping("/profile")
    public ResponseEntity<ProfileResponse> updateProfile(
            @AuthenticationPrincipal String email,
            @Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateProfile(email, request));
    }

    // PUT /api/user/change-password
    @PutMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            @AuthenticationPrincipal String email,
            @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(email, request.getCurrentPassword(),
                request.getNewPassword(), passwordEncoder);
        return ResponseEntity.ok(Map.of("message", "Password updated successfully"));
    }
}