package net.hamza.banque.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "clients")
@Data
public class Client extends Utilisateur {

    private Long numeroClient;
    private String adresse;
    private String ville;
    private String codePostal;
    private String pays;

    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    private String cin;

    @OneToOne
    private Carte carte;
    @OneToMany
    private List<Compte> comptes = new ArrayList<>();
    @OneToMany
    private List<Feedback> feedbacks = new ArrayList<>();





}