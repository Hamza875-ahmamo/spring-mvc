package net.hamza.banque.repository;

import net.hamza.banque.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepo extends JpaRepository<Client, Integer> {
    Optional<Client> findByEmail(String email);
}
