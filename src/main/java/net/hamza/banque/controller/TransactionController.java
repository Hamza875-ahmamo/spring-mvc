package net.hamza.banque.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
@CrossOrigin(origins = "*")
public class TransactionController {
    @GetMapping("/history/{count}")
    public String getTransactionList(@PathVariable int count) {

        return "List of transactions with count: " + count;
    }

}
