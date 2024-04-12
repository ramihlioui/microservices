package tn.esprit.myfirstproject.services;

import tn.esprit.myfirstproject.dto.UniversiteDTO;
import tn.esprit.myfirstproject.entities.Universite;

import java.util.List;

public interface IUniversiteServices {
    List<UniversiteDTO> retrieveAllUniversites();
    Universite addUniversite(Universite b);
    Universite updateUniversite (Universite b);
    String deleteUniversite (  long idUniversite);

    Universite retrieveUniversite (long idUniversite);
     Universite getUniversiteparnom(String nom);
     void affecteUniFoy(String idFoyer ,String nomUni);
}