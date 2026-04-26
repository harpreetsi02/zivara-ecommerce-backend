package com.zivara.backend.config;

import com.zivara.backend.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    // OncePerRequestFilter — har request pe sirf ek baar chalega

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Request header se token nikalo
        String authHeader = request.getHeader("Authorization");

        // Token format: "Bearer xxxxx.yyyyy.zzzzz"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Token nahi hai — aage jaane do (public routes ke liye)
            filterChain.doFilter(request, response);
            return;
        }

        // "Bearer " hata ke sirf token lo
        String token = authHeader.substring(7);

        // Token valid hai?
        if (!jwtUtil.isTokenValid(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token");
            return;
        }

        // Token se email aur role nikalo
        String email = jwtUtil.extractEmail(token);
        String role = jwtUtil.extractRole(token);

        // Spring Security ko batao ki yeh user authenticated hai
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role))
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Aage jaane do
        filterChain.doFilter(request, response);
    }
}