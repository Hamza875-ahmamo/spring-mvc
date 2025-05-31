package net.hamza.banque.service.impl;

import net.hamza.banque.model.LoginActivity;
import net.hamza.banque.model.User;
import net.hamza.banque.repository.LoginActivityRepository;
import net.hamza.banque.repository.UserRepository;
import net.hamza.banque.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginActivityRepository loginActivityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void enableTwoFactorAuth(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setTwoFactorEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void disableTwoFactorAuth(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setTwoFactorEnabled(false);
        userRepository.save(user);
    }

    @Override
    public boolean verifyTwoFactorCode(Long userId, String code) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Implementation of 2FA verification logic
        return true; // Placeholder
    }

    @Override
    public List<LoginActivity> getLoginHistory(Long userId) {
        return loginActivityRepository.findByUserIdOrderByLoginTimeDesc(userId);
    }

    @Override
    public void recordLoginActivity(Long userId, String ipAddress, String deviceInfo) {
        LoginActivity activity = new LoginActivity();
        activity.setUserId(userId);
        activity.setIpAddress(ipAddress);
        activity.setDeviceInfo(deviceInfo);
        loginActivityRepository.save(activity);
    }

    @Override
    public void lockAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setAccountLocked(true);
        userRepository.save(user);
    }

    @Override
    public void unlockAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setAccountLocked(false);
        userRepository.save(user);
    }

    @Override
    public boolean isAccountLocked(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.isAccountLocked();
    }

    @Override
    public void updateSecurityPreferences(Long userId, boolean twoFactorEnabled, boolean securityAlerts) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setTwoFactorEnabled(twoFactorEnabled);
        user.setSecurityAlerts(securityAlerts);
        userRepository.save(user);
    }
} 