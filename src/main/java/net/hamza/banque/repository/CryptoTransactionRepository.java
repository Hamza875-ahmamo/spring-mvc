package net.hamza.banque.repository;

import net.hamza.banque.model.CryptoTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CryptoTransactionRepository extends JpaRepository<CryptoTransaction, Long> {
    List<CryptoTransaction> findByUserId(Long userId);
    List<CryptoTransaction> findByUserIdAndCryptoCurrencySymbol(Long userId, String symbol);
    List<CryptoTransaction> findByUserIdAndTransactionDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    List<CryptoTransaction> findByUserIdAndType(Long userId, String type);
    List<CryptoTransaction> findByUserIdAndStatus(Long userId, String status);
} 