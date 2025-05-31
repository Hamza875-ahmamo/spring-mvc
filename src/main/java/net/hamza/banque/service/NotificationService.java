package net.hamza.banque.service;

import net.hamza.banque.model.Notification;
import java.util.List;

public interface NotificationService {
    void sendEmailNotification(String to, String subject, String content);
    void sendSMSNotification(String phoneNumber, String message);
    void saveNotification(Notification notification);
    List<Notification> getUserNotifications(Long userId);
    void markNotificationAsRead(Long notificationId);
    void deleteNotification(Long notificationId);
    void sendSecurityAlert(String userId, String alertType, String message);
    void sendTransactionAlert(String userId, String transactionType, double amount);
} 