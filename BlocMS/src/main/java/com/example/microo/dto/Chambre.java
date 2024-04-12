package com.example.microo.dto;

import com.example.microo.dto.type.TypeChambre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chambre {

	private Long idChambre;
	private long numeroChambre;

	private TypeChambre typeC;
	private long idBloc;
}
