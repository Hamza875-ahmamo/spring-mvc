package net.hamza.banque.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "agents")
@Data

public class Agent extends Utilisateur {
    private String codeAgent;
    private String agence;



}