package tn.esprit.myfirstproject.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import tn.esprit.myfirstproject.client.FoyerClient;
import tn.esprit.myfirstproject.dto.Foyer;
import tn.esprit.myfirstproject.dto.UniversiteDTO;
import tn.esprit.myfirstproject.entities.Universite;
import tn.esprit.myfirstproject.repositories.IUniversiteRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class IUniversiteServicesImp implements IUniversiteServices {

    private final  IUniversiteRepository iUniversiteRepository ;


    private FoyerClient foyerClient;

    @Override
    public List<UniversiteDTO> retrieveAllUniversites() {
        List<Universite> list =  iUniversiteRepository.findAll() ;
        List<Foyer> foyerList = foyerClient.getListFoyer();
        List<UniversiteDTO> result = new ArrayList<>();
        list.forEach(e->  {
                   UniversiteDTO uno = UniversiteDTO.builder()
                           .idUniversite(e.getIdUniversite())
                           .adresse(e.getAdresse())
                           .nomUniversite(e.getNomUniversite())
                           .build();

                    for (Foyer f : foyerList){
                        if(e.getIdfoyer()==f.getIdFoyer()){
                            uno.setNomFoyer(f.getNomFoyer() != null ? f.getNomFoyer() : null);
                        }
                    }

                    result.add(uno);
        });


        return result;
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

    @Override
    public void affecteUniFoy(String idFoyer, String nomUni) {

        Universite uni = iUniversiteRepository.findUniversiteByNomUniversite(nomUni);

        uni.setIdfoyer(Long.parseLong(idFoyer));

        iUniversiteRepository.save(uni);
        log.info("uni affect foyer");

    }

}
