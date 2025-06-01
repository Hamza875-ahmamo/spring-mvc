package net.hamza.banque.repository;

import net.hamza.banque.model.Bank;
import net.hamza.banque.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepo extends JpaRepository<Bank, Integer> {
}
