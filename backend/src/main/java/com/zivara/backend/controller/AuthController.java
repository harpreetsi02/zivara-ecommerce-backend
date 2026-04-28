package com.zivara.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zivara.backend.dto.AuthResponse;
import com.zivara.backend.dto.LoginRequest;
import com.zivara.backend.dto.RegisterRequest;
import com.zivara.backend.service.AuthService;

import jakarta.validation.Valid;

@RestController           // yeh ek REST API controller
@RequestMapping("/api/auth")  // saare routes /api/auth se shuru honge saare
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        // @Valid — RegisterRequest ke validations check karega
        // @RequestBody — JSON body ko Java object mein convert karega

        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}