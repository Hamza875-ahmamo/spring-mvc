package net.hamza.banque.service;

import net.hamza.banque.dto.TransactionRequest;
import net.hamza.banque.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    // Création et exécution des transactions
    Transaction createTransaction(TransactionRequest request);
    void executeTransaction(Transaction transaction);
    
    // Récupération des transactions
    List<Transaction> getRecentTransactions(Long accountId);
    List<Transaction> getScheduledTransactions();
    List<Transaction> getTransactionsByDateRange(Long accountId, LocalDateTime startDate, LocalDateTime endDate);
    List<Transaction> getTransactionsByType(String type);
    List<Transaction> getTransactionsByStatus(String status);
    
    // Recherche et filtrage
    List<Transaction> searchTransactions(String query);
    
    // Gestion des transactions
    void cancelTransaction(Long transactionId);
} 