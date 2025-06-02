package net.hamza.banque.dto;

import lombok.Data;
import net.hamza.banque.model.PaiementCarte;
import net.hamza.banque.model.Recharge;
import net.hamza.banque.model.Virement;

import java.util.List;
@Data
public class Transactions {
    private List<Virement> virements;
    private List<Recharge> recharges;
    private List<PaiementCarte> paiementCartes;
}
