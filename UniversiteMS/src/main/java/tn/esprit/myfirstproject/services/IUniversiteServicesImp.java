package tn.esprit.myfirstproject.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import tn.esprit.myfirstproject.entities.Universite;
import tn.esprit.myfirstproject.repositories.IUniversiteRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class IUniversiteServicesImp implements IUniversiteServices {

    private final  IUniversiteRepository iUniversiteRepository ;

    @Override
    public List<Universite> retrieveAllUniversites() {
        return iUniversiteRepository.findAll() ;
    }

    @Override
    public Universite addUniversite(Universite u) {
        return iUniversiteRepository.save (u) ;
    }

    @Override
    public Universite updateUniversite(Universite u) {
        return iUniversiteRepository.save(u);
    }

    @Override
    public String  deleteUniversite(long idUniversite) {
        iUniversiteRepository.deleteById (idUniversite) ;
        return "l'universite est supprimée";
    }

    @Override
    public Universite retrieveUniversite(long idUniversite) {
        return iUniversiteRepository.findById(idUniversite).orElse(null);
    }

    @Override
    public Universite getUniversiteparnom(String nom) {
        return iUniversiteRepository.findUniversiteByNomUniversite(nom);
    }


}
