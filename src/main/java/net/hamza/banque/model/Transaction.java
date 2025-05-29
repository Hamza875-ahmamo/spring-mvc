package net.hamza.banque.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@DiscriminatorColumn(name = "transaction_type")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private Double monet;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTransaction;

    private TypeTransaction typeTransaction;
    @ManyToOne
    @JoinColumn( nullable = false)
    private Compte compteDebite;
    @ManyToOne
    @JoinColumn( nullable = false)
    private Compte compteCredite;

}