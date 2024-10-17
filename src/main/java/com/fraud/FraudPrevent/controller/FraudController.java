package com.fraud.FraudPrevent.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fraud.FraudPrevent.dto.FraudMetricsDTO;
import com.fraud.FraudPrevent.excepciones.ErrorResponse;
import com.fraud.FraudPrevent.excepciones.UserNotFoundException;
import com.fraud.FraudPrevent.model.Usuario;
import com.fraud.FraudPrevent.service.FraudDetectionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/fraud")
public class FraudController {

	
	private final FraudDetectionService fraudDetectionService;
	private static final Logger logger = LoggerFactory.getLogger(FraudController.class);
    

    public FraudController(FraudDetectionService fraudDetectionService) {
        this.fraudDetectionService = fraudDetectionService;
    }
    

    //Verificacion del estado del Usuario, para evaluar el riesgo
    @GetMapping("/checkFraudRisk")
    @Operation(summary = "Verificación de riesgo de fraude", description = "Esta operación verifica el riesgo de fraude para un usuario específico.")	

    @ApiResponse(responseCode = "200", 
	    description = "Consulta de riesgo de fraude verificado con éxito.",
	    content = @Content(
	        mediaType = "application/json",
	        schema = @Schema(implementation = FraudMetricsDTO.class, 
	                         description = "Variables de devolucion de riesgo de fraude del usuario."),
	        examples = @ExampleObject(value = "{\"is_new_user\": false, \"qty_rejected_1d\": 2, \"total_amt_7d\": 500.0}")))
    
    
    @ApiResponse(responseCode = "404", 
	    description = "Usuario no encontrado o sin ningún pago efectuado.",
	    content = @Content(
	        mediaType = "application/json",
	        schema = @Schema(implementation = ErrorResponse.class)))
    
    @PreAuthorize("hasRole('USER')") // Requiere que el usuario tenga el rol USER
    
    public ResponseEntity<FraudMetricsDTO> checkFraudRisk(@Parameter(description = "ID del usuario", required = true) @RequestParam Long userId) {
    	    	
    	if (userId == null || userId <= 0) {
   		 throw new IllegalArgumentException("El ID del usuario no es válido.");
       }
    	
    	logger.info("Solicitando checkFraudRisk para el usuario: {} ", userId);
    	
    	return getFraudMetricsResponse(userId);
    }
    
    
    /**
     * Método privado que encapsula la lógica de obtención de métricas de fraude.
     * 
     * @param userId: ID del usuario para el cual se solicitan las métricas.
     * @return ResponseEntity: con las métricas de fraude o un código 404 si no se encuentra el usuario.
     */
    private ResponseEntity<FraudMetricsDTO> getFraudMetricsResponse(Long userId) {
    	   	 
        Usuario usuario = fraudDetectionService.findUserById(userId);
        if (usuario == null) {
        	throw new UserNotFoundException("Usuario no encontrado con ID: " + userId);
        }

        FraudMetricsDTO metrics = fraudDetectionService.getFraudMetrics(usuario);
        return ResponseEntity.ok(metrics);
    }
    
    
    
    
   

}
