package com.example.microo.client;

import com.example.microo.dto.Chambre;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class ChambreClient {


	private RestClient restClient;



	public Chambre getChambres(Long id){
		log.info("calling chambre-ms");

		String url =  "http://localhost:8090/chambre/afficherchambre/{idchambre}";

		return restClient.get().uri(url,id).retrieve().body(Chambre.class);

	}

	public Chambre affecteChambre(Chambre c){
		log.info("calling chambre-ms");

		String url =  "http://localhost:8090/chambre/modifierchambre";

		return restClient.put().uri(url).body(c).retrieve().body(Chambre.class);

	}

}
