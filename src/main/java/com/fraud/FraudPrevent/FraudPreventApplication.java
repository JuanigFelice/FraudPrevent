package com.fraud.FraudPrevent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class FraudPreventApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(FraudPreventApplication.class, args);
	}

	// Método para permitir que el .war funcione en un servidor externo
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(FraudPreventApplication.class);
    }
    
}
