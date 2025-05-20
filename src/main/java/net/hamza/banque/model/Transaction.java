package net.hamza.banque.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double monet;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTransaction;

    private String estAtt;
    private String reference;
    private String typeTransaction;
    private String description;


}