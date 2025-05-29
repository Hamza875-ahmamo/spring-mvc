package net.hamza.banque.controller;

import lombok.RequiredArgsConstructor;
import net.hamza.banque.model.Transaction;
import net.hamza.banque.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
@CrossOrigin(origins = "*")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/history/{count}")
    public ResponseEntity<List<Transaction>> getTransactionList(@PathVariable int count) {
        try{
            return ResponseEntity.ok(transactionService.getAll(count));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable long id) {
        try {
            Transaction transaction = transactionService.getById(id);
            if (transaction != null) {
                return ResponseEntity.ok(transaction);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @PostMapping("/sameClient")
    public ResponseEntity<String> sameClientTransactions(@RequestParam long debiteId, @RequestParam long crediteId, @RequestParam double montant) {
        try {
            transactionService.sameClientTransactions(debiteId, crediteId, montant);
            return ResponseEntity.ok("Transaction successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Transaction failed: " + e.getMessage());
        }
    }

}
