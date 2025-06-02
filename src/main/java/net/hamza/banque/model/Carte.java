package net.hamza.banque.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "cartes")
@Data
public class Carte {
    @Id
    private String numericCarte;

    private String titulaire;
    private Date dateExpiratration;
    private String codeSecurite;
    private String typeCrain;
    private Double platformRetrait;
    private Double platformPaiement;
    private Boolean estActive;
    private String statue;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;




}