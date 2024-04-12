package tn.esprit.myfirstproject.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tn.esprit.myfirstproject.dto.Foyer;

import java.util.List;

@Component
@Slf4j
public class FoyerClient {


	@Autowired
	private RestClient restClient;




	public List<Foyer> getListFoyer(){
		log.info("start getFoyerList");

		String url ="http://host.docker.internal:8090/foyer/";

		return restClient.get().uri(url).retrieve().body(new ParameterizedTypeReference<List<Foyer>>(){});

	}




}
