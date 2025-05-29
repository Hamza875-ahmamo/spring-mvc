package net.hamza.banque.repository;

import net.hamza.banque.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepo extends JpaRepository<Compte, Long> {
    // Additional query methods can be defined here if needed
}
