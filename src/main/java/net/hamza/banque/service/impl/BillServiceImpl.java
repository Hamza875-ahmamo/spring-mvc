package net.hamza.banque.service.impl;

import lombok.RequiredArgsConstructor;
import net.hamza.banque.model.Bill;
import net.hamza.banque.repository.BillRepository;
import net.hamza.banque.service.BillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;

    @Override
    @Transactional
    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    @Transactional
    public Bill updateBill(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    @Transactional
    public void deleteBill(Long billId) {
        billRepository.deleteById(billId);
    }

    @Override
    public Bill getBillById(Long billId) {
        return billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    @Override
    public List<Bill> getUserBills(Long userId) {
        return billRepository.findByUserId(userId);
    }

    @Override
    public List<Bill> getBillsByStatus(Long userId, String status) {
        return billRepository.findByUserIdAndStatus(userId, status);
    }

    @Override
    public List<Bill> getBillsByProviderType(Long userId, String providerType) {
        return billRepository.findByUserIdAndProviderType(userId, providerType);
    }

    @Override
    public List<Bill> getBillsByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return billRepository.findByUserIdAndDueDateBetween(userId, startDate, endDate);
    }

    @Override
    public List<Bill> searchBills(Long userId, String query) {
        return billRepository.findByUserIdAndProviderNameContainingIgnoreCase(userId, query);
    }

    @Override
    @Transactional
    public void markBillAsPaid(Long billId) {
        Bill bill = getBillById(billId);
        bill.setStatus("PAID");
        bill.setPaidAt(LocalDateTime.now());
        billRepository.save(bill);
    }

    @Override
    @Transactional
    public void updateBillStatus(Long billId, String status) {
        Bill bill = getBillById(billId);
        bill.setStatus(status);
        billRepository.save(bill);
    }

    @Override
    public double getTotalBillsAmount(Long userId) {
        return getUserBills(userId).stream()
                .mapToDouble(bill -> bill.getAmount().doubleValue())
                .sum();
    }

    @Override
    public double getUpcomingBillsAmount(Long userId) {
        return getBillsByStatus(userId, "UPCOMING").stream()
                .mapToDouble(bill -> bill.getAmount().doubleValue())
                .sum();
    }
} 