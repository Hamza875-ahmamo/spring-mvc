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
    private String compteSource;
    private String compteDestination;
    private String motif;


}