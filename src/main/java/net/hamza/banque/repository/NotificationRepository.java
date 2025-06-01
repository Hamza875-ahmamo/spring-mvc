package net.hamza.banque.repository;

import net.hamza.banque.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    List<Notification> findByIsReadAndUserId(boolean isRead, Long userId);
    List<Notification> findByType(String type);
} 