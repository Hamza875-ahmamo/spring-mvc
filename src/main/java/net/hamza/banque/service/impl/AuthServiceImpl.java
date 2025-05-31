package net.hamza.banque.service.impl;

import lombok.RequiredArgsConstructor;
import net.hamza.banque.dto.LoginRequest;
import net.hamza.banque.dto.LoginResponse;
import net.hamza.banque.model.User;
import net.hamza.banque.repository.UserRepository;
import net.hamza.banque.security.JwtTokenProvider;
import net.hamza.banque.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        // Authentifier l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Générer les tokens
        String token = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        // Récupérer les informations de l'utilisateur
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Mettre à jour la dernière connexion
        user.setLastLoginDate(LocalDateTime.now());
        userRepository.save(user);

        // Construire la réponse
        return LoginResponse.builder()
            .token(token)
            .refreshToken(refreshToken)
            .userEmail(user.getEmail())
            .userName(user.getFirstName() + " " + user.getLastName())
            .role(user.getRole())
            .isFirstLogin(user.getLastLoginDate() == null)
            .message("Login successful")
            .build();
    }

    @Override
    public void logout(String token) {
        // Invalider le token
        jwtTokenProvider.invalidateToken(token);
        SecurityContextHolder.clearContext();
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        if (jwtTokenProvider.validateToken(refreshToken)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
            String newToken = jwtTokenProvider.generateToken(authentication);
            String newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication);

            User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

            return LoginResponse.builder()
                .token(newToken)
                .refreshToken(newRefreshToken)
                .userEmail(user.getEmail())
                .userName(user.getFirstName() + " " + user.getLastName())
                .role(user.getRole())
                .message("Token refreshed successfully")
                .build();
        }
        throw new RuntimeException("Invalid refresh token");
    }

    @Override
    @Transactional
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Générer un token de réinitialisation
        String resetToken = UUID.randomUUID().toString();
        user.setResetPasswordToken(resetToken);
        user.setResetPasswordTokenExpiry(LocalDateTime.now().plusHours(24));
        userRepository.save(user);

        // TODO: Envoyer un email avec le lien de réinitialisation
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetPasswordToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid reset token"));

        if (user.getResetPasswordTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token has expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        user.setResetPasswordTokenExpiry(null);
        userRepository.save(user);
    }

    @Override
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }
} 