package net.hamza.banque.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "agents")
@Data

public class Agent extends Utilisateur {
    public Agent() {
        this.setRole(Role.AGENT);
    }

    @ManyToOne
    private Bank bank;




}