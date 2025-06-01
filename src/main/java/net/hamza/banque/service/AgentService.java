package net.hamza.banque.service;

import lombok.RequiredArgsConstructor;
import net.hamza.banque.model.Agent;
import net.hamza.banque.model.Bank;
import net.hamza.banque.model.Client;
import net.hamza.banque.repository.AgentRepo;
import net.hamza.banque.repository.BankRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentService {
    private final AgentRepo agentRepo;
    private final BankRepo bankRepo;


    public Agent getAgent(int id) {
        return  agentRepo.getReferenceById(id);

    }
    public List<Agent> getAllAgents() {
        return agentRepo.findAll();
    }
    public void saveAgent(Agent agent) {
        agentRepo.save(agent);
    }
    public void updateAgent(Agent agent) {
        agentRepo.save(agent);
    }
    public void deleteAgent(int id) {
        agentRepo.deleteById(id);
    }
    public void DeleteClientFromBank(Client client,Bank bank) {
        bank.getClients().remove(client);
        bankRepo.save(bank);
    }
    public List<Client> getAllClientsFromBank(Bank bank) {
        return bank.getClients();
    }

}
