package tn.esprit.myfirstproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@ComponentScan
public class AppConfig {


	/**
	 * RestClient Bean
	 *
	 * @return RestClient restClient.
	 */
	@Bean
	public RestClient restClient()  {
		return RestClient.builder().build();
	}
}
