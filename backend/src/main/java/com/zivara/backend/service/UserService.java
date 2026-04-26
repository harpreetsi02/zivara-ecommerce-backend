package com.zivara.backend.service;

import com.zivara.backend.dto.ProfileResponse;
import com.zivara.backend.dto.UpdateProfileRequest;
import com.zivara.backend.model.User;
import com.zivara.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Profile fetch karo
    public ProfileResponse getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toResponse(user);
    }

    // Profile update karo
    public ProfileResponse updateProfile(String email, UpdateProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setGender(request.getGender());
        user.setDob(request.getDob());

        return toResponse(userRepository.save(user));
    }

    // Password change karo
    public void changePassword(String email, String currentPassword,
                               String newPassword,
                               org.springframework.security.crypto.password.PasswordEncoder encoder) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

    private ProfileResponse toResponse(User user) {
        return new ProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getGender(),
                user.getDob(),
                user.getRole().name()
        );
    }
}