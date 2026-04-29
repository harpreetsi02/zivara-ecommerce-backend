package com.zivara.backend.service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${resend.api.key}")
    private String apiKey;

    @Value("${resend.from.email}")
    private String fromEmail;

    public void sendOtpEmail(String toEmail, String otp) {
        try {
            Resend resend = new Resend(apiKey);

            String html = """
                <div style="font-family: Arial, sans-serif; max-width: 500px; margin: 0 auto; padding: 20px; background: #ffffff;">

                    <div style="text-align: center; padding: 20px 0; border-bottom: 1px solid #f0f0f0;">
                        <h1 style="font-size: 24px; letter-spacing: 8px; color: #000000; margin: 0;">ZIVARA</h1>
                        <p style="color: #999; font-size: 12px; margin: 5px 0 0 0;">Fashion Store</p>
                    </div>

                    <div style="padding: 30px 0; text-align: center;">
                        <p style="color: #333; font-size: 16px; margin-bottom: 8px;">Hello! 👋</p>
                        <p style="color: #666; font-size: 14px; margin-bottom: 25px;">
                            Use the code below to verify your email address.
                        </p>

                        <div style="background: #f8f8f8; border-radius: 12px; padding: 25px; display: inline-block; margin: 0 auto;">
                            <p style="color: #999; font-size: 12px; margin: 0 0 8px 0; text-transform: uppercase; letter-spacing: 2px;">Verification Code</p>
                            <h2 style="font-size: 40px; letter-spacing: 10px; color: #000000; margin: 0; font-weight: bold;">%s</h2>
                        </div>

                        <p style="color: #999; font-size: 12px; margin-top: 20px;">
                            ⏱ Valid for <strong>5 minutes</strong> only.
                        </p>
                        <p style="color: #999; font-size: 12px;">
                            🔒 Do not share this code with anyone.
                        </p>
                    </div>

                    <div style="border-top: 1px solid #f0f0f0; padding-top: 15px; text-align: center;">
                        <p style="color: #ccc; font-size: 11px; margin: 0;">
                            If you didn't create an account with Zivara, please ignore this email.
                        </p>
                    </div>

                </div>
            """.formatted(otp);

            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from(fromEmail)
                    .to(toEmail)
                    .subject("Your Zivara Verification Code — " + otp)
                    .html(html)
                    .build();

            CreateEmailResponse response = resend.emails().send(params);
            System.out.println("✅ Email sent successfully. ID: " + response.getId());

        } catch (ResendException e) {
            System.err.println("❌ Email sending failed: " + e.getMessage());
            throw new RuntimeException("Failed to send verification email. Please try again.");
        }
    }
}