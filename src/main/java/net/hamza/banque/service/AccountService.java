package net.hamza.banque.service;

import net.hamza.banque.model.Account;
import java.util.List;

public interface AccountService {
    Account getAccountById(Long id);
    List<Account> getAllAccounts();
    Account createAccount(Account account);
    Account updateAccount(Account account);
    void deleteAccount(Long id);
    double getAccountBalance(Long accountId);
    void transferMoney(Long fromAccountId, Long toAccountId, double amount);
    List<Account> getAccountsByUserId(Long userId);
} 