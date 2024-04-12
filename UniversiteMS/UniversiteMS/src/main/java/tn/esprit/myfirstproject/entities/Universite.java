package tn.esprit.myfirstproject.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Universite implements Serializable {

    //identifiant d'une universite
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idUniversite;

    // le nom d'une universite
    @Column(name="nomUniversite", unique=true)
    String nomUniversite;

    // l'addresse d'une universite
    String adresse;

    //L'identifiant du foyer affecté a l'universite
    Long idfoyer;

    public Long getIdUniversite() {
        return idUniversite;
    }

    public void setIdUniversite(Long idUniversite) {
        this.idUniversite = idUniversite;
    }

    public String getNomUniversite() {
        return nomUniversite;
    }

    public void setNomUniversite(String nomUniversite) {
        this.nomUniversite = nomUniversite;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Long getIdfoyer() {
        return idfoyer;
    }

    public void setIdfoyer(Long idfoyer) {
        this.idfoyer = idfoyer;
    }
}
