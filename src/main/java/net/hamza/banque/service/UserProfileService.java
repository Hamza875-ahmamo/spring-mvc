package net.hamza.banque.service;

import net.hamza.banque.model.UserProfile;
import java.util.List;

public interface UserProfileService {
    UserProfile getUserProfile(Long userId);
    UserProfile updateUserProfile(UserProfile userProfile);
    void updateContactInfo(Long userId, String email, String phone, String address);
    void updatePersonalInfo(Long userId, String firstName, String lastName, String dateOfBirth);
    void updatePreferences(Long userId, boolean emailNotifications, boolean smsAlerts, boolean marketing);
    void updateLanguagePreference(Long userId, String language);
    void updateAccessibilitySettings(Long userId, boolean highContrast, boolean largerText);
    List<String> getSupportedLanguages();
} 