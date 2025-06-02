package net.hamza.banque.service;

import lombok.RequiredArgsConstructor;
import net.hamza.banque.model.Agent;
import net.hamza.banque.model.Client;
import net.hamza.banque.repository.AgentRepo;
import net.hamza.banque.repository.ClientRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentService {
    private final AgentRepo agentRepo;
    private final ClientRepo clientRepo;
    public void creerClient(Client client,Long  AgentId) {
        Agent agent = agentRepo.findById(AgentId)
                .orElseThrow(() -> new RuntimeException("Agent not found with id: " + AgentId));
        Client nouveauClient = clientRepo.save(client);
        List<Client> clients = agent.getCleients();
        clients.add(nouveauClient);
        agent.setCleients(clients);
        agentRepo.save(agent);




    }
    public void supprimerClient(Long clientId, Long agentId) {
        Agent agent = agentRepo.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent not found with id: " + agentId));
        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));
        List<Client> clients = agent.getCleients();
        clients.remove(client);
        agent.setCleients(clients);
        agentRepo.save(agent);
        clientRepo.delete(client);
    }
    public void desactiverClient(Long clientId, Long agentId) {
        Agent agent = agentRepo.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent not found with id: " + agentId));
        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));
        client.isAccountNonLocked();
        clientRepo.save(client);
    }

}
