package net.hamza.banque.service;

import net.hamza.banque.model.Utilisateur;
import net.hamza.banque.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtulisateurService {
    private final UtilisateurRepository repository;

    public UtulisateurService(UtilisateurRepository repository) {
        this.repository = repository;
    }

    public List<Utilisateur> getAllUtulisateur() {
        return repository.findAll();
    }

    public Utilisateur saveUtulisateur(Utilisateur user ) {
        return repository.save(user);
    }

    public void deleteUtulisateur(Long id) {
        repository.deleteById(id);
    }}
