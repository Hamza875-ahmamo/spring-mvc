package net.hamza.banque.repository;

import net.hamza.banque.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

}