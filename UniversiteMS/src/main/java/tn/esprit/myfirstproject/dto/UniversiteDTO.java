package tn.esprit.myfirstproject.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UniversiteDTO {

	Long idUniversite;


	String nomUniversite;

	// l'addresse d'une universite
	String adresse;

	//L'identifiant du foyer affecté a l'universite
	String nomFoyer;


}
