package net.hamza.banque.repository;

import net.hamza.banque.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    // Additional query methods can be defined here if needed
}
