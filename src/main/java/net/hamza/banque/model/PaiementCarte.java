package net.hamza.banque.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "paiement_cartes")
@Data
public class PaiementCarte extends Transaction{
    @Id
    private String numericCarte;

    private String codeMarkData;
    private String terminal;
    private Boolean statue;

    @OneToOne
    @JoinColumn(name = "carte_id")
    private Carte carte;


}