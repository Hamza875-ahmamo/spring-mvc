package net.hamza.banque.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "comptes")
@Data
public class Compte {
    @Id
    private String numericCompte;

    private Double solde;
    private boolean statue;


    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;


    private String typeCompte;


}