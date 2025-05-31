package net.hamza.banque.repository;

import net.hamza.banque.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByUserId(Long userId);
    List<Bill> findByUserIdAndStatus(Long userId, String status);
    List<Bill> findByUserIdAndProviderType(Long userId, String providerType);
    List<Bill> findByUserIdAndDueDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    List<Bill> findByUserIdAndProviderNameContainingIgnoreCase(Long userId, String providerName);
    List<Bill> findByUserIdAndAccountNumber(Long userId, String accountNumber);
} 