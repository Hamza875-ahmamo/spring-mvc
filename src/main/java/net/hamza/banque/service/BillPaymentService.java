package net.hamza.banque.service;

import net.hamza.banque.model.BillPayment;

import java.time.LocalDateTime;
import java.util.List;

public interface BillPaymentService {
    // Création et gestion des paiements
    BillPayment createPayment(BillPayment payment);
    BillPayment updatePayment(BillPayment payment);
    void cancelPayment(Long paymentId);
    BillPayment getPaymentById(Long paymentId);
    
    // Récupération des paiements
    List<BillPayment> getBillPayments(Long billId);
    List<BillPayment> getAccountPayments(Long accountId);
    List<BillPayment> getPaymentsByStatus(String status);
    List<BillPayment> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    // Paiements récurrents
    void setupRecurringPayment(BillPayment payment, String frequency);
    void cancelRecurringPayment(Long paymentId);
    List<BillPayment> getUpcomingRecurringPayments();
    
    // Exécution des paiements
    void processPayment(BillPayment payment);
    void processRecurringPayments();
} 