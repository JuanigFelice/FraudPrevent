package com.fraud.FraudPrevent.config; // Asegúrate de que el paquete sea correcto

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException; // Usando jakarta.servlet
import jakarta.servlet.http.HttpServletRequest; // Usando jakarta.servlet.http
import jakarta.servlet.http.HttpServletResponse; // Usando jakarta.servlet.http

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

	
	
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
    	
    	logger.error("Unauthorized error: {}", authException.getMessage());
    	
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
