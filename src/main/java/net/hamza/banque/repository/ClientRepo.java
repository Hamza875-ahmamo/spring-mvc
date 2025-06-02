package net.hamza.banque.repository;

import net.hamza.banque.model.Agent;
import net.hamza.banque.model.Client;
import net.hamza.banque.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {

}
