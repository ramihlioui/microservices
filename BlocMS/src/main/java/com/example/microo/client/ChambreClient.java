package com.example.microo.client;

import com.example.microo.dto.Chambre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
@Scope(value = "singleton")
public class ChambreClient {



	@Autowired
	private RestClient restClient;



	public Chambre getChambres(Long id){
		log.info("calling chambre-ms");

		String url =  "http://host.docker.internal:8090/chambre/afficherchambre/{idchambre}";
		log.info(url);

		return restClient.get().uri(url,id).retrieve().body(Chambre.class);

	}

	public Chambre affecteChambre(Chambre c){
		log.info("calling chambre-ms");

		String url =  "http://host.docker.internal:8090/chambre/modifierchambre";
		log.info(url);
		return restClient.put().uri(url).body(c).retrieve().body(Chambre.class);

	}

}
