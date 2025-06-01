package net.hamza.banque.controller;

import lombok.RequiredArgsConstructor;
import net.hamza.banque.model.Bank;
import net.hamza.banque.model.Client;
import net.hamza.banque.service.AgentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/agent")
public class AgentController {

    private final AgentService agentService;
    @GetMapping
    public List<Client> getClientsBank(@RequestParam Bank bank) {
        return agentService.getAllClientsFromBank(bank);
    }

}
