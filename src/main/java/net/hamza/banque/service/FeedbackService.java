package net.hamza.banque.service;

import lombok.RequiredArgsConstructor;
import net.hamza.banque.model.Agent;
import net.hamza.banque.model.Client;
import net.hamza.banque.model.Feedback;
import net.hamza.banque.repository.AgentRepo;
import net.hamza.banque.repository.ClientRepo;
import net.hamza.banque.repository.FeedbackRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepo feedbackRepo;
    private final ClientRepo clientRepo;
    private final AgentRepo agentRepo;
    public void save(Feedback feedback,Long clientId, Long agentId) {
        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Agent agent = agentRepo.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent not found"));
        feedback.setClient(client);
        feedback.setAgent(agent);
        feedbackRepo.save(feedback);
    }




    public Feedback updateFeedback(Long id, Feedback updatedFeedback) {
        Feedback feedback = feedbackRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback non trouvé avec l'ID: " + id));

        feedback.setMotif(updatedFeedback.getMotif());
        feedback.setDetail(updatedFeedback.getDetail());

        return feedbackRepo.save(feedback);
    }
    public void deleteFeedback(Long id) {
        Feedback feedback = feedbackRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback non trouvé avec l'ID: " + id));

        feedbackRepo.delete(feedback);
    }
}
