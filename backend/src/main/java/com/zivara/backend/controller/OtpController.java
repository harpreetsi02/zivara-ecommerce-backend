package com.zivara.backend.controller;

import com.zivara.backend.dto.SendOtpRequest;
import com.zivara.backend.dto.VerifyOtpRequest;
import com.zivara.backend.service.OtpService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    // POST /api/otp/send
    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendOtp(
            @Valid @RequestBody SendOtpRequest request) {
        otpService.sendOtp(request.getEmail());
        return ResponseEntity.ok(Map.of(
                "message", "OTP sent to " + request.getEmail()
        ));
    }

    // POST /api/otp/verify
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyOtp(
            @Valid @RequestBody VerifyOtpRequest request) {
        boolean verified = otpService.verifyOtp(request.getEmail(), request.getOtp());
        return ResponseEntity.ok(Map.of(
                "verified", verified,
                "message", "Email verified successfully!"
        ));
    }
}