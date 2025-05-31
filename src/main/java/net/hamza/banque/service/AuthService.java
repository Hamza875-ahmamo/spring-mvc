package net.hamza.banque.service;

import net.hamza.banque.dto.LoginRequest;
import net.hamza.banque.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    void logout(String token);
    LoginResponse refreshToken(String refreshToken);
    void forgotPassword(String email);
    void resetPassword(String token, String newPassword);
    boolean validateToken(String token);
} 