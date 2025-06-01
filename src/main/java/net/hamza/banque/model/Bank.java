package net.hamza.banque.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Bank {
    @Id
    private int idAgence;
    private String nom;
    private String Localisation;
    @OneToMany
    private List<Client> clients;
    @OneToMany
    private List<Agent> agents;
}
