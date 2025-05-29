package net.hamza.banque.service;

import jakarta.transaction.Transactional;
import net.hamza.banque.model.Compte;
import net.hamza.banque.model.Transaction;
import net.hamza.banque.repository.CompteRepo;
import net.hamza.banque.repository.TransactionRepo;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final CompteRepo compteRepo;
    TransactionService(TransactionRepo transactionRepo,CompteRepo compteRepo) {
        this.transactionRepo = transactionRepo;
        this.compteRepo = compteRepo;
    }
    public List<Transaction> getAll(int count) {
        return transactionRepo.findAll().subList(0, Math.min(count, transactionRepo.findAll().size()));
    }
    public Transaction getById(long id) {
        return transactionRepo.findById(id).orElse(null);
    }
    @Transactional
    public void sameClientTransactions(long DebiteId, long CrediteId,double montant) {
        Compte compteCredite = compteRepo.findById(CrediteId).get();
        Compte compteDebite = compteRepo.findById(DebiteId).get();
        compteDebite.setSolde(compteDebite.getSolde() - montant);
        compteCredite.setSolde(compteCredite.getSolde() + montant);
        Transaction transaction = new Transaction();
        transaction.setMonet(montant);
        transaction.setDateTransaction(new Timestamp(System.currentTimeMillis()));
        transaction.setCompteDebite(compteDebite);
        transaction.setCompteCredite(compteCredite);
        transactionRepo.save(transaction);
        compteRepo.save(compteDebite);
        compteRepo.save(compteCredite);
    }
}
