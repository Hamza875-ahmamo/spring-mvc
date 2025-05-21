package net.hamza.banque.service;

import net.hamza.banque.model.Carte;

import java.util.Optional;

public interface CarteService {
    Optional<Carte> findCarteByClientEmail(String email);
}
