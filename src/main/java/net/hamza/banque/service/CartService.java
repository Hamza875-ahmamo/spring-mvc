package net.hamza.banque.service;

import lombok.RequiredArgsConstructor;
import net.hamza.banque.model.Carte;
import net.hamza.banque.model.Client;
import net.hamza.banque.repository.CarteRepo;
import net.hamza.banque.repository.ClientRepo;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ClientRepo clientRepo;
    private final CarteRepo carteRepo;

    public void addToCart(Carte carte, Long clientId) {
        Client client= clientRepo.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));
        carte.setClient(client);
        carteRepo.save(carte);
    }
    public void desactiverCarte(Long numericCarte) {
        Carte carte = carteRepo.findById(numericCarte)
                .orElseThrow(() -> new RuntimeException("Carte non trouvée avec l'ID: " + numericCarte));

        carte.setEstActive(false);
        carte.setStatue("DESACTIVEE");
        carteRepo.save(carte);
    }
    public void activerCarte(Long numericCarte) {
        Carte carte = carteRepo.findById(numericCarte)
                .orElseThrow(() -> new RuntimeException("Carte non trouvée avec l'ID: " + numericCarte));

        carte.setEstActive(true);
        carte.setStatue("ACTIVE");
        carteRepo.save(carte);
    }

    public void supprimerCarte(Long numericCarte) {
        Carte carte = carteRepo.findById(numericCarte)
                .orElseThrow(() -> new RuntimeException("Carte non trouvée avec l'ID: " + numericCarte));
        carteRepo.delete(carte);
    }
    public void renouvelerCarteExpiree(Long numericCarte) {
        Carte carte = carteRepo.findById(numericCarte)
                .orElseThrow(() -> new RuntimeException("Carte non trouvée avec l'ID: " + numericCarte));
        Date today = new Date();
        if (carte.getDateExpiratration().before(today)) {
            Date nouvelleDate = carte.getDateExpiratration();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nouvelleDate);
            calendar.add(Calendar.YEAR, 4);
            carte.setDateExpiratration(calendar.getTime());
            if (!carte.getEstActive() && "EXPIREE".equals(carte.getStatue())) {
                carte.setEstActive(true);
                carte.setStatue("ACTIVE");
            }

            carteRepo.save(carte);
        }
    }


}
