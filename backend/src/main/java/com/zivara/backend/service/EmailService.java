package com.zivara.backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String toEmail, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Your Zivara Verification Code");
            helper.setFrom("tumeragmail@gmail.com");

            // HTML email
            String html = """
                <div style="font-family: Arial, sans-serif; max-width: 500px; margin: 0 auto; padding: 20px;">
                    <div style="text-align: center; margin-bottom: 30px;">
                        <h1 style="font-size: 28px; letter-spacing: 8px; color: #000;">ZIVARA</h1>
                    </div>
                    <div style="background: #f9f9f9; border-radius: 12px; padding: 30px; text-align: center;">
                        <p style="color: #666; font-size: 14px; margin-bottom: 10px;">Your verification code is</p>
                        <h2 style="font-size: 42px; letter-spacing: 12px; color: #000; margin: 10px 0;">%s</h2>
                        <p style="color: #999; font-size: 12px; margin-top: 15px;">
                            Valid for 5 minutes. Do not share this code with anyone.
                        </p>
                    </div>
                    <p style="color: #ccc; font-size: 11px; text-align: center; margin-top: 20px;">
                        If you didn't request this, please ignore this email.
                    </p>
                </div>
            """.formatted(otp);

            helper.setText(html, true); // true = HTML
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}