package net.hamza.banque.model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private TypeCompte typeCompte;
    @OneToMany
    @Fetch(FetchMode.JOIN)
    private List<Transaction> transactions = new ArrayList<>();


}