package net.hamza.banque.service.impl;

import net.hamza.banque.model.Notification;
import net.hamza.banque.repository.NotificationRepository;
import net.hamza.banque.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmailNotification(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    @Override
    public void sendSMSNotification(String phoneNumber, String message) {
        // Implementation for SMS sending service
        // This would typically integrate with an SMS gateway service
    }

    @Override
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public void markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Override
    public void sendSecurityAlert(String userId, String alertType, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setType("SECURITY");
        notification.setMessage(message);
        notification.setAlertType(alertType);
        saveNotification(notification);
        
        // Send email notification for security alerts
        sendEmailNotification(userId, "Security Alert: " + alertType, message);
    }

    @Override
    public void sendTransactionAlert(String userId, String transactionType, double amount) {
        String message = String.format("Transaction Alert: %s of $%.2f has been processed on your account.", 
            transactionType, amount);
        
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setType("TRANSACTION");
        notification.setMessage(message);
        saveNotification(notification);
        
        // Send email notification for transaction alerts
        sendEmailNotification(userId, "Transaction Alert", message);
    }
} 