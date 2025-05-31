package net.hamza.banque.repository;

import net.hamza.banque.model.BillPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillPaymentRepository extends JpaRepository<BillPayment, Long> {
    List<BillPayment> findByBillId(Long billId);
    List<BillPayment> findByAccountId(Long accountId);
    List<BillPayment> findByStatus(String status);
    List<BillPayment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<BillPayment> findByIsRecurringTrueAndNextPaymentDateBefore(LocalDateTime date);
    List<BillPayment> findByBillUserId(Long userId);
} 