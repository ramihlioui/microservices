package tn.esprit.myfirstproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Foyer implements Serializable {

	private Long idFoyer;
	private String nomFoyer;
	private Long capaciteFoyer;
	private String gerants;
	private String adresse;
	private String fillesougarcons;
	private String contact;
	private String photo;
	private String pointgoogle;
	private Long iduniversite;



}
