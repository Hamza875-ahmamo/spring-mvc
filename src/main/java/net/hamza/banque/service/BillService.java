package net.hamza.banque.service;

import net.hamza.banque.model.Bill;

import java.time.LocalDateTime;
import java.util.List;

public interface BillService {
    // Gestion des factures
    Bill createBill(Bill bill);
    Bill updateBill(Bill bill);
    void deleteBill(Long billId);
    Bill getBillById(Long billId);
    
    // Récupération des factures
    List<Bill> getUserBills(Long userId);
    List<Bill> getBillsByStatus(Long userId, String status);
    List<Bill> getBillsByProviderType(Long userId, String providerType);
    List<Bill> getBillsByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    List<Bill> searchBills(Long userId, String query);
    
    // Gestion des paiements
    void markBillAsPaid(Long billId);
    void updateBillStatus(Long billId, String status);
    
    // Statistiques
    double getTotalBillsAmount(Long userId);
    double getUpcomingBillsAmount(Long userId);
} 