package com.fraud.FraudPrevent.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fraud.FraudPrevent.dto.CurrencyConversionResponseDTO;

@Service
public class CurrencyConversionService {

	// Clave de API de RapidAPI que se inyectan desde la configuración de la aplicación
	@Value("${rapidapi.key}")
    private String rapidApiKey;

	// Host de RapidAPI que se inyectan desde la configuración de la aplicación
    @Value("${rapidapi.host}")
    private String rapidApiHost;

    // Instancia de RestTemplate para realizar solicitudes HTTP
    private final RestTemplate restTemplate;

    // Constructor para inyectar el RestTemplate
    public CurrencyConversionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    
    /**
     * Convierte una cantidad de una moneda a otra utilizando la API de RapidAPI.
     * 
     * @param from la moneda de origen (por ejemplo, "ARS")
     * @param to la moneda de destino (por ejemplo, "USD")
     * @param amount la cantidad a convertir
     * @return un objeto CurrencyConversionResponse que contiene el resultado de la conversión
     */
    
    public CurrencyConversionResponseDTO convertCurrency(String from, String to, double amount) {
    	
        String url = String.format("https://currency-conversion-and-exchange-rates.p.rapidapi.com/convert?from=%s&to=%s&amount=%s", 
                                    from, to, amount);
        
        // Crea los encabezados HTTP y establece la clave de API y el host
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", rapidApiKey);
        headers.set("x-rapidapi-host", rapidApiHost);
        
        // Crea una entidad HTTP con los encabezados
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        return restTemplate.exchange(url, HttpMethod.GET, entity, CurrencyConversionResponseDTO.class).getBody();
    }
    
}
