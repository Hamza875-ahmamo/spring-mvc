package net.hamza.banque.controller;

import net.hamza.banque.model.Utilisateur;
import net.hamza.banque.service.UtulisateurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/utilisateurs")
public class UtilisateurController {
    private final UtulisateurService service;

    public UtilisateurController( UtulisateurService service) {
        this.service = service;
    }

    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return service.getAllUtulisateur();
    }


    @PostMapping
    public Utilisateur saveUtilisateur(@RequestBody Utilisateur utilisateur) {
        return service.saveUtulisateur(utilisateur);
    }



    @DeleteMapping("/{id}")
    public void deleteUtilisateur(@PathVariable Long id) {
        service.deleteUtulisateur(id);
    }




}