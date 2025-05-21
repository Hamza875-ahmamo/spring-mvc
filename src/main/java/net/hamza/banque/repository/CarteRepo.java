package net.hamza.banque.repository;

import net.hamza.banque.model.Carte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CarteRepo extends JpaRepository<Carte, Long> {

}
