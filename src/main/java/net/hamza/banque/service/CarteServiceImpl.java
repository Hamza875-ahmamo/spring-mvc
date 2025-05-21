package net.hamza.banque.service;

import net.hamza.banque.model.Carte;
import net.hamza.banque.model.Client;
import net.hamza.banque.repository.CarteRepo;
import net.hamza.banque.repository.ClientRepo;
import net.hamza.banque.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CarteServiceImpl implements CarteService {
    private final CarteRepo carteRepository;
    private final ClientRepo clientRepository;

    @Autowired
    public CarteServiceImpl(CarteRepo carteRepository, ClientRepo clientRepository) {
        this.carteRepository = carteRepository;
        this.clientRepository = clientRepository;

    }

    @Override
    public Optional<Carte> findCarteByClientEmail(String email) {
        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            return Optional.ofNullable(client.getCarte());
        }

        return Optional.empty();
    }
}


