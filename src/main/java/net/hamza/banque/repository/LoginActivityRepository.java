package net.hamza.banque.repository;

import net.hamza.banque.model.LoginActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginActivityRepository extends JpaRepository<LoginActivity, Long> {
    List<LoginActivity> findByUserId(Long userId);
    List<LoginActivity> findByUserIdOrderByLoginDateDesc(Long userId);
    List<LoginActivity> findByStatus(String status);
} 