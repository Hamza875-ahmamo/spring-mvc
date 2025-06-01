package net.hamza.banque.service.impl;

import lombok.RequiredArgsConstructor;
import net.hamza.banque.dto.TransactionRequest;
import net.hamza.banque.model.Account;
import net.hamza.banque.model.Transaction;
import net.hamza.banque.repository.AccountRepository;
import net.hamza.banque.repository.TransactionRepository;
import net.hamza.banque.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public Transaction createTransaction(TransactionRequest request) {
        // Vérifier le compte source
        Account fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        // Vérifier le solde suffisant
        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        // Créer la transaction
        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setAmount(request.getAmount());
        transaction.setType("TRANSFER");
        transaction.setStatus("PENDING");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setNote(request.getNote());
        transaction.setIsScheduled(request.isScheduled());
        transaction.setScheduledDate(request.getScheduledDate());

        // Gérer le destinataire selon le type
        switch (request.getRecipientType()) {
            case "MY_ACCOUNT":
                Account toAccount = accountRepository.findById(request.getToAccountId())
                        .orElseThrow(() -> new RuntimeException("Destination account not found"));
                transaction.setToAccount(toAccount);
                break;
            case "OTHER_ACCOUNT":
                transaction.setRecipientName(request.getRecipientName());
                transaction.setRecipientAccountNumber(request.getRecipientAccountNumber());
                break;
            case "EXTERNAL":
                transaction.setRecipientName(request.getRecipientName());
                transaction.setRecipientAccountNumber(request.getRecipientAccountNumber());
                transaction.setBankName(request.getBankName());
                transaction.setRoutingNumber(request.getRoutingNumber());
                break;
            default:
                throw new RuntimeException("Invalid recipient type");
        }

        // Si la transaction n'est pas programmée, l'exécuter immédiatement
        if (!request.isScheduled()) {
            executeTransaction(transaction);
        }

        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void executeTransaction(Transaction transaction) {
        // Mettre à jour le solde du compte source
        Account fromAccount = transaction.getFromAccount();
        fromAccount.setBalance(fromAccount.getBalance().subtract(transaction.getAmount()));
        accountRepository.save(fromAccount);

        // Si c'est un transfert interne, mettre à jour le compte destination
        if (transaction.getToAccount() != null) {
            Account toAccount = transaction.getToAccount();
            toAccount.setBalance(toAccount.getBalance().add(transaction.getAmount()));
            accountRepository.save(toAccount);
        }

        // Mettre à jour le statut de la transaction
        transaction.setStatus("COMPLETED");
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getRecentTransactions(Long accountId) {
        return transactionRepository.findTop10ByFromAccountIdOrToAccountIdOrderByTransactionDateDesc(accountId, accountId);
    }

    @Override
    public List<Transaction> getScheduledTransactions() {
        return transactionRepository.findByIsScheduledTrueAndScheduledDateAfter(LocalDateTime.now());
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(Long accountId, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByTransactionDateBetween(startDate, endDate);
    }

    @Override
    public List<Transaction> getTransactionsByType(String type) {
        return transactionRepository.findByType(type);
    }

    @Override
    public List<Transaction> getTransactionsByStatus(String status) {
        return transactionRepository.findByStatus(status);
    }

    @Override
    public List<Transaction> searchTransactions(String query) {
        // Recherche par nom de bénéficiaire
        List<Transaction> byRecipient = transactionRepository.findByRecipientNameContainingIgnoreCase(query);
        if (!byRecipient.isEmpty()) {
            return byRecipient;
        }

        // Recherche par note
        return transactionRepository.findByNoteContainingIgnoreCase(query);
    }

    @Override
    public void cancelTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!"PENDING".equals(transaction.getStatus())) {
            throw new RuntimeException("Cannot cancel a non-pending transaction");
        }

        transaction.setStatus("CANCELLED");
        transactionRepository.save(transaction);
    }
} 