package br.com.rodrigoamora.eventosti.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI custonOpenAPI() {
		return new OpenAPI()
						.info(new Info()
						.title("API Eventos TI")
						.description("Documentação da API")
						.version("1.0"));
	}
	
}
