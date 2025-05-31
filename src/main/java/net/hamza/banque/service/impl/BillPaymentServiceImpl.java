package net.hamza.banque.service.impl;

import lombok.RequiredArgsConstructor;
import net.hamza.banque.model.Account;
import net.hamza.banque.model.Bill;
import net.hamza.banque.model.BillPayment;
import net.hamza.banque.repository.AccountRepository;
import net.hamza.banque.repository.BillPaymentRepository;
import net.hamza.banque.service.BillPaymentService;
import net.hamza.banque.service.BillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillPaymentServiceImpl implements BillPaymentService {

    private final BillPaymentRepository billPaymentRepository;
    private final BillService billService;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public BillPayment createPayment(BillPayment payment) {
        return billPaymentRepository.save(payment);
    }

    @Override
    @Transactional
    public BillPayment updatePayment(BillPayment payment) {
        return billPaymentRepository.save(payment);
    }

    @Override
    @Transactional
    public void cancelPayment(Long paymentId) {
        BillPayment payment = getPaymentById(paymentId);
        if ("PENDING".equals(payment.getStatus())) {
            payment.setStatus("CANCELLED");
            billPaymentRepository.save(payment);
        } else {
            throw new RuntimeException("Cannot cancel a non-pending payment");
        }
    }

    @Override
    public BillPayment getPaymentById(Long paymentId) {
        return billPaymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Override
    public List<BillPayment> getBillPayments(Long billId) {
        return billPaymentRepository.findByBillId(billId);
    }

    @Override
    public List<BillPayment> getAccountPayments(Long accountId) {
        return billPaymentRepository.findByAccountId(accountId);
    }

    @Override
    public List<BillPayment> getPaymentsByStatus(String status) {
        return billPaymentRepository.findByStatus(status);
    }

    @Override
    public List<BillPayment> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return billPaymentRepository.findByPaymentDateBetween(startDate, endDate);
    }

    @Override
    @Transactional
    public void setupRecurringPayment(BillPayment payment, String frequency) {
        payment.setRecurring(true);
        payment.setRecurringFrequency(frequency);
        calculateNextPaymentDate(payment);
        billPaymentRepository.save(payment);
    }

    @Override
    @Transactional
    public void cancelRecurringPayment(Long paymentId) {
        BillPayment payment = getPaymentById(paymentId);
        payment.setRecurring(false);
        payment.setRecurringFrequency(null);
        payment.setNextPaymentDate(null);
        billPaymentRepository.save(payment);
    }

    @Override
    public List<BillPayment> getUpcomingRecurringPayments() {
        return billPaymentRepository.findByIsRecurringTrueAndNextPaymentDateBefore(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void processPayment(BillPayment payment) {
        // Vérifier le solde du compte
        Account account = accountRepository.findById(payment.getAccount().getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance().compareTo(payment.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        // Déduire le montant du compte
        account.setBalance(account.getBalance().subtract(payment.getAmount()));
        accountRepository.save(account);

        // Mettre à jour le statut du paiement
        payment.setStatus("COMPLETED");
        payment.setPaymentDate(LocalDateTime.now());
        billPaymentRepository.save(payment);

        // Marquer la facture comme payée
        billService.markBillAsPaid(payment.getBill().getId());
    }

    @Override
    @Transactional
    public void processRecurringPayments() {
        List<BillPayment> upcomingPayments = getUpcomingRecurringPayments();
        for (BillPayment payment : upcomingPayments) {
            try {
                processPayment(payment);
                calculateNextPaymentDate(payment);
                billPaymentRepository.save(payment);
            } catch (Exception e) {
                payment.setStatus("FAILED");
                billPaymentRepository.save(payment);
            }
        }
    }

    private void calculateNextPaymentDate(BillPayment payment) {
        LocalDateTime currentDate = payment.getNextPaymentDate() != null ? 
                payment.getNextPaymentDate() : LocalDateTime.now();

        switch (payment.getRecurringFrequency()) {
            case "MONTHLY":
                payment.setNextPaymentDate(currentDate.plusMonths(1));
                break;
            case "QUARTERLY":
                payment.setNextPaymentDate(currentDate.plusMonths(3));
                break;
            case "ANNUALLY":
                payment.setNextPaymentDate(currentDate.plusYears(1));
                break;
            default:
                throw new RuntimeException("Invalid recurring frequency");
        }
    }
} 