package com.zivara.backend.service;

import com.zivara.backend.model.OtpVerification;
import com.zivara.backend.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class OtpService {

    private final OtpRepository otpRepository;
    private final EmailService emailService;

    @Value("${otp.expiration.minutes}")
    private int otpExpirationMinutes;

    public OtpService(OtpRepository otpRepository, EmailService emailService) {
        this.otpRepository = otpRepository;
        this.emailService = emailService;
    }

    @Transactional
    public void sendOtp(String email) {
        // Purane OTPs delete karo
        otpRepository.deleteByEmail(email);

        // OTP generate karo
        String otp = generateOtp();

        // Database mein save karo
        OtpVerification otpVerification = new OtpVerification();
        otpVerification.setEmail(email);
        otpVerification.setOtp(otp);
        otpVerification.setExpiresAt(
                LocalDateTime.now().plusMinutes(otpExpirationMinutes)
        );
        otpRepository.save(otpVerification);

        // Email bhejo
        emailService.sendOtpEmail(email, otp);
    }

    @Transactional
    public boolean verifyOtp(String email, String otp) {
        OtpVerification otpVerification = otpRepository
                .findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() -> new RuntimeException("OTP not found. Please request a new one."));

        // Expire check
        if (LocalDateTime.now().isAfter(otpVerification.getExpiresAt())) {
            otpRepository.delete(otpVerification);
            throw new RuntimeException("OTP expired. Please request a new one.");
        }

        // OTP match check
        if (!otpVerification.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP. Please try again.");
        }

        // Mark verified
        otpVerification.setVerified(true);
        otpRepository.save(otpVerification);
        return true;
    }

    public boolean isEmailVerified(String email) {
        return otpRepository.findTopByEmailOrderByIdDesc(email)
                .map(OtpVerification::isVerified)
                .orElse(false);
    }

    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        return String.valueOf(100000 + random.nextInt(900000));
    }
}