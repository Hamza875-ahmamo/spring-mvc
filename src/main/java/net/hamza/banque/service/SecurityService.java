package net.hamza.banque.service;

import net.hamza.banque.model.LoginActivity;
import java.util.List;

public interface SecurityService {
    void changePassword(Long userId, String currentPassword, String newPassword);
    void enableTwoFactorAuth(Long userId);
    void disableTwoFactorAuth(Long userId);
    boolean verifyTwoFactorCode(Long userId, String code);
    List<LoginActivity> getLoginHistory(Long userId);
    void recordLoginActivity(Long userId, String ipAddress, String deviceInfo);
    void lockAccount(Long userId);
    void unlockAccount(Long userId);
    boolean isAccountLocked(Long userId);
    void updateSecurityPreferences(Long userId, boolean twoFactorEnabled, boolean securityAlerts);
} 