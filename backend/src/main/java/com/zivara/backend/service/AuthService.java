package com.zivara.backend.service;

import com.zivara.backend.dto.AuthResponse;
import com.zivara.backend.dto.LoginRequest;
import com.zivara.backend.dto.RegisterRequest;
import com.zivara.backend.model.Role;
import com.zivara.backend.model.User;
import com.zivara.backend.repository.UserRepository;
import com.zivara.backend.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service  // Spring ko batata hai ki yeh ek Service bean hai
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final OtpService otpService;

    // Constructor Injection — Lombok nahi use kar rahe toh khud likhenge
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       OtpService otpService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.otpService = otpService;
    }

    // ── REGISTER ──
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Email verified hai check karo
        if (!otpService.isEmailVerified(request.getEmail())) {
            throw new RuntimeException("Email not verified. Please verify with OTP first.");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getName(), user.getEmail(), user.getRole().name());
    }

    // ── LOGIN ──
    public AuthResponse login(LoginRequest request) {

        // Email se user dhundo
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Password check karo
        // BCrypt match karta hai — plain text vs encrypted
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Token generate karo aur return karo
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getName(), user.getEmail(), user.getRole().name());
    }
}