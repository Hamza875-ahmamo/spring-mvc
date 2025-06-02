package net.hamza.banque.repository;

import net.hamza.banque.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteRepo extends JpaRepository<Compte, String> {
}