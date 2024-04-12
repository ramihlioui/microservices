package tn.esprit.myfirstproject.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.myfirstproject.dto.UniversiteDTO;
import tn.esprit.myfirstproject.entities.Universite;
import tn.esprit.myfirstproject.repositories.IUniversiteRepository;
import tn.esprit.myfirstproject.services.IUniversiteServices;

import java.util.List;

@RestController
@RequestMapping("/universite")
@RequiredArgsConstructor
public class UniversiteController {

    private final   IUniversiteServices iUniversiteService ;

    // retourne la liste de toutes les universite
    @GetMapping("/afficheruniversites")
    List<UniversiteDTO> retrieveAllUniversites() {
        return iUniversiteService.retrieveAllUniversites();
    }

    //Ajoute une universite
    @PostMapping("/ajouteruniversite")
    Universite addUniversite(@RequestBody Universite universite){
        return iUniversiteService.addUniversite(universite);
    }

    //Modifier une universite
    @PutMapping("/modifieruniversite")
    Universite updateUniversite (@RequestBody Universite universite){
        return iUniversiteService.updateUniversite(universite);
    }

    //Supprime une universite
    @DeleteMapping("/deleteUniversite/{idUniversite}")
    public String deleteUniversite(@PathVariable long idUniversite ) {
        iUniversiteService.deleteUniversite(idUniversite);
        return "l'université est supprimée";
    }

    //recherche une universite par son identifiant en parametre
    @GetMapping("/chercheruniversiteparid/{iduniversite}")
    Universite retrieveUniversite(@PathVariable("iduniversite") long idUniversite){return iUniversiteService.retrieveUniversite(idUniversite);}

    //recherche une universite par son nom en parametre
    @GetMapping("/chercheruniversiteparnom/{nom}")
    Universite getuniversiteparnom(@PathVariable("nom") String nom){return iUniversiteService.getUniversiteparnom(nom);}


    @PutMapping("/affecterFoyer/{idFoyer}/{nomUniversite}")
    void getuniversiteparnom(@PathVariable("idFoyer") String idFoyer,@PathVariable("nomUniversite") String nomUniversite){
         iUniversiteService.affecteUniFoy(idFoyer,nomUniversite);}

}
