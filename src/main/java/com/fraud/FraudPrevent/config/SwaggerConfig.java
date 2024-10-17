package com.fraud.FraudPrevent.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;


	@OpenAPIDefinition(
	    info = @Info(
	        title = "Fraud Detection API",
	        version = "1.0",
	        description = "API para la prevencion de riesgo de fraude de usuarios.",
	        contact = @Contact(
	            name = "Soporte Técnico",
	            email = "juan.felice@yahoo.com.ar"
	        ),
	        license = @License(
	            name = "Licencia MIT",
	            url = "https://opensource.org/licenses/MIT"
	        )
	    )
	)

	@Configuration
	public class SwaggerConfig {		
	
}
