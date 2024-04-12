package com.example.microo.services;

import com.example.microo.client.ChambreClient;
import com.example.microo.dto.Chambre;
import com.example.microo.entities.Bloc;
import com.example.microo.repositories.BlocRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class BlocServiceImpl implements IBlocService {
    BlocRepo blocRepo;

    ChambreClient chambreClient;

    @Override
    public List<Bloc> retrieveBlocs() {
        return blocRepo.findAll();
    }

    @Override
    public Bloc updateBloc(Bloc bloc) {
        return blocRepo.save(bloc);
    }

    @Override
    public Bloc addBloc(Bloc bloc) {
        bloc.setSature(false);
        return blocRepo.save(bloc);
    }

    @Override
    public Bloc retrieveBloc(long idBloc) {
        return blocRepo.findById(idBloc).orElse(null);
    }


    @Override
    public void removeBloc(long idBloc) {
        blocRepo.deleteById(idBloc);
    }

    @Override
    public Bloc updateBloc2(Long idBloc, Bloc bloc) {
        Bloc bloc1 = blocRepo.findById(idBloc).get();
        bloc1.setNomBloc(bloc.getNomBloc());
        bloc1.setCapaciteBloc(bloc.getCapaciteBloc());

        return blocRepo.save(bloc1);
    }
   public Bloc affecterChaabmresABloc(Long numchambre, String nomBloc){
        Bloc bloc = blocRepo.findBlocByNomBloc(nomBloc);


        Chambre chambre = chambreClient.getChambres(numchambre);



            if (bloc.getCapaciteBloc() > 0 ) { // Vérifier si la capacité est supérieure à zéro
                chambre.setIdBloc(bloc.getIdBloc());
                if (bloc != null) {
                    bloc.setCapaciteBloc(bloc.getCapaciteBloc() + 1); // Incrémenter la capacité de l'ancien bloc
                    if (bloc != null && (bloc.getSature() == null || bloc.getSature())) {
                        bloc.setSature(false); // Mettre à jour l'état de saturation de l'ancien bloc si nécessaire
                    }
                }
                bloc.setCapaciteBloc(bloc.getCapaciteBloc() - 1);

                if (bloc.getCapaciteBloc() == 0) {
                    bloc.setSature(true);
                }else{
                    System.out.println("Désolé, le bloc est saturé. Aucune chambre n'a été ajoutée.");

                }

                chambreClient.affecteChambre(chambre);

            }


        return bloc;
    }

    @Override
    public List<Bloc> rechercheParNomBloc(String nomBloc) {
        return blocRepo.findByNomBlocContainingIgnoreCase(nomBloc);
    }
}

