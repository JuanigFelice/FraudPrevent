package com.fraud.FraudPrevent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	
	@Value("${cors.allowed.origins}") // Inyecta el valor desde application.properties
    private String allowedOrigins;
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/fraud/checkFraudRisk/{userId}") // Asegúrate de permitir solo las rutas necesarias
        		.allowedOrigins(allowedOrigins.split(","))
                .allowedMethods("GET") // Solo permite métodos GET
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}

