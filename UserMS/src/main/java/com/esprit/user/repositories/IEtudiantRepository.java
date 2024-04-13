package com.esprit.user.repositories;

import com.esprit.user.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEtudiantRepository extends JpaRepository<Etudiant, Long> {
    Etudiant findByCin(Long cin);
}
