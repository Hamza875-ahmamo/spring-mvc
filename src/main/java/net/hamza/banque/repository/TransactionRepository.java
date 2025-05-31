package net.hamza.banque.repository;

import net.hamza.banque.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromAccountId(Long accountId);
    List<Transaction> findByToAccountId(Long accountId);
    List<Transaction> findByType(String type);
    List<Transaction> findByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Transaction> findByIsScheduledTrueAndScheduledDateAfter(LocalDateTime currentDate);
    List<Transaction> findTop10ByFromAccountIdOrToAccountIdOrderByTransactionDateDesc(Long fromAccountId, Long toAccountId);
    List<Transaction> findByStatus(String status);
    List<Transaction> findByAmountGreaterThanEqual(BigDecimal amount);
    List<Transaction> findByRecipientNameContainingIgnoreCase(String recipientName);
    List<Transaction> findByNoteContainingIgnoreCase(String note);
} 