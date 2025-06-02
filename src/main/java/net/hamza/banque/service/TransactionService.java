package net.hamza.banque.service;

import lombok.RequiredArgsConstructor;
import net.hamza.banque.dto.ResponseTransaction;
import net.hamza.banque.dto.Transactions;
import net.hamza.banque.model.*;
import net.hamza.banque.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {


    private final CompteRepo compteRepo;

    private final RechargeRepo rechargeRepo;
    private final PaiementFacureRepo paiementFacureRepo;
    private final VirementRepo virementRepo;

    public Transactions getTransactionsByCompte(String compteId) {

        if (compteId == null) {
            throw new IllegalArgumentException("Le numéro de compte ne peut pas être nul");
        }


        Compte compte = compteRepo.findById(compteId)
                .orElseThrow(() -> new IllegalArgumentException("Compte introuvable : " + compteId));

        Transactions transaction =new Transactions() ;
        transaction.setVirements(compte.getVirements());
        transaction.setPaiementCartes(compte.getPaiementCartes());
        transaction.setRecharges(compte.getRecharges());

        return transaction;
    }
    public void addRecharge(Compte compte, Recharge recharge) {

        this.rechargeRepo.save(recharge);
        List<Recharge> recharges=compte.getRecharges();
        recharges.add(recharge);
        compte.setRecharges(recharges);
        compte.setSolde(compte.getSolde()-recharge.getMontant());
        compteRepo.save(compte);
    }




    public ResponseTransaction addVirement(Virement virement) {

        this.virementRepo.save(virement);

        Compte compteDebit =virement.getCompteSource();
        List<Virement> virements=compteDebit.getVirements();
        virements.add(virement);
        compteDebit.setVirements(virements);
        if (compteDebit.getSolde() < virement.getMontant()) {
            return ResponseTransaction.builder()
                    .message("Solde insuffisant pour effectuer le virement")
                    .statue(false)
                    .build();
        }
        compteDebit.setSolde(compteDebit.getSolde()-virement.getMontant());
        Compte compteDest=virement.getCompteDestination();
        List<Virement> virement1 = compteDebit.getVirements();
        virement1.add(virement);
        compteDest.setVirements(virement1);
        compteDest.setSolde(compteDest.getSolde()+virement.getMontant());
        compteRepo.save(compteDest);
        compteRepo.save(compteDebit);
        return ResponseTransaction.builder()
                .message("Virement effectué avec succès")
                .statue(true)
                .build();



    }



}