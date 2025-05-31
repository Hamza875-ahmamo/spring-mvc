package net.hamza.banque.service.impl;

import net.hamza.banque.model.UserProfile;
import net.hamza.banque.repository.UserProfileRepository;
import net.hamza.banque.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserProfile getUserProfile(Long userId) {
        return userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));
    }

    @Override
    public UserProfile updateUserProfile(UserProfile userProfile) {
        if (!userProfileRepository.existsById(userProfile.getId())) {
            throw new RuntimeException("User profile not found");
        }
        return userProfileRepository.save(userProfile);
    }

    @Override
    public void updateContactInfo(Long userId, String email, String phone, String address) {
        UserProfile profile = getUserProfile(userId);
        profile.setEmail(email);
        profile.setPhone(phone);
        profile.setAddress(address);
        userProfileRepository.save(profile);
    }

    @Override
    public void updatePersonalInfo(Long userId, String firstName, String lastName, String dateOfBirth) {
        UserProfile profile = getUserProfile(userId);
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setDateOfBirth(dateOfBirth);
        userProfileRepository.save(profile);
    }

    @Override
    public void updatePreferences(Long userId, boolean emailNotifications, boolean smsAlerts, boolean marketing) {
        UserProfile profile = getUserProfile(userId);
        profile.setEmailNotifications(emailNotifications);
        profile.setSmsAlerts(smsAlerts);
        profile.setMarketingEnabled(marketing);
        userProfileRepository.save(profile);
    }

    @Override
    public void updateLanguagePreference(Long userId, String language) {
        UserProfile profile = getUserProfile(userId);
        profile.setLanguage(language);
        userProfileRepository.save(profile);
    }

    @Override
    public void updateAccessibilitySettings(Long userId, boolean highContrast, boolean largerText) {
        UserProfile profile = getUserProfile(userId);
        profile.setHighContrast(highContrast);
        profile.setLargerText(largerText);
        userProfileRepository.save(profile);
    }

    @Override
    public List<String> getSupportedLanguages() {
        return Arrays.asList("en", "es", "fr", "de", "zh");
    }
} 