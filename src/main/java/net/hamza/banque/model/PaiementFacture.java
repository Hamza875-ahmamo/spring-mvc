package net.hamza.banque.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "paiement_cartes")
@Data
public class PaiementFacture extends Transaction{

    private String numeroFacture;

    private String terminal;
    private ServiceType serviceType;


    @ManyToOne
    private Compte compte;


}