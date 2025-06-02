package net.hamza.banque.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "virements")
@Data
@DiscriminatorValue("VIREMENT")
public class Virement extends Transaction {
    @ManyToOne
    private Compte compteSource;
    @ManyToOne
    private Compte compteDestination;
    private String motif;


}