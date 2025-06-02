package net.hamza.banque.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comptes")
@Data
@NoArgsConstructor

public class Compte {

    @Id
    private String numericCompte;

    private Double solde = 0.0;

    private Boolean statut = true; // Renommé de 'statue' à 'statut'

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation = new Date();

    private String typeCompte;



    @OneToMany
    private List<Virement> virements = new ArrayList<Virement>();
    @OneToMany
    private List<Recharge> recharges = new ArrayList<Recharge>();
    @OneToMany
    private List<PaiementFacture> paiementCartes = new ArrayList<PaiementFacture>();
}