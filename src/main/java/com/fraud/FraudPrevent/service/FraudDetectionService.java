package com.fraud.FraudPrevent.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fraud.FraudPrevent.controller.FraudController;
import com.fraud.FraudPrevent.dto.CurrencyConversionResponseDTO;
import com.fraud.FraudPrevent.dto.FraudMetricsDTO;
import com.fraud.FraudPrevent.excepciones.UserNotFoundException;
import com.fraud.FraudPrevent.model.Pagos;
import com.fraud.FraudPrevent.model.Usuario;
import com.fraud.FraudPrevent.repository.PagosRepository;
import com.fraud.FraudPrevent.repository.UsuarioRepository;

@Service
public class FraudDetectionService {
	
	@Autowired
	private final UsuarioRepository usuarioRepository;
	@Autowired
	private final PagosRepository pagosRepository;
    @Autowired
    private final CurrencyConversionService currencyConversionService;
	private String monedaLocal;
    
    private static final Logger logger = LoggerFactory.getLogger(FraudController.class);


    public FraudDetectionService(UsuarioRepository usuarioRepository, PagosRepository pagosRepository, CurrencyConversionService currencyConversionService) {
        this.usuarioRepository = usuarioRepository;
        this.pagosRepository = pagosRepository;
		this.currencyConversionService = currencyConversionService;
    }

    
    public FraudMetricsDTO getFraudMetrics(Usuario usuario) {
    	if (usuario == null) {
            throw new UserNotFoundException("El usuario no puede ser nulo");
        }
    	
        boolean is_new_user 				= isNewUser(usuario);
        int qty_rejected_1d 				= getRejectedPaymentsLastDay(usuario);
        double totalMonedaLocalUltSemana	= getTotalAmountLastWeek(usuario);
        
        // Convertir moneda local a USD
        
        double totalAmountLastWeekInUsd = convertirMonedaAUsd(totalMonedaLocalUltSemana, this.monedaLocal);

        return new FraudMetricsDTO(is_new_user, qty_rejected_1d, totalAmountLastWeekInUsd);
    }
    
    
    public boolean isNewUser(Usuario usuario) {
    	
    	if (usuario == null) {
            throw new UserNotFoundException("El usuario no puede ser nulo");
        }
    	
    	LocalDateTime currentDateTime 	= LocalDateTime.now(); 		// Obtener la fecha y hora actual
        LocalDateTime sevenDaysAgo 		= currentDateTime.minusDays(7); // Calcular la fecha y hora de hace 7 días

        return usuario.getFechaCreacionCta() != null && usuario.getFechaCreacionCta().isAfter(sevenDaysAgo);
    }

    public int getRejectedPaymentsLastDay(Usuario usuario) {
    	
    	if (usuario == null) {
            throw new UserNotFoundException("El usuario no puede ser nulo");
        }
    	
    	LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime startOfYesterday = yesterday.atStartOfDay(); 		// 00:00:00 del día anterior
        LocalDateTime endOfYesterday = yesterday.atTime(LocalTime.MAX); // 23:59:59.999999999 del día anterior

        List<Pagos> rejectedPayments = pagosRepository.findByUserAndEstadoDelPagoAndFechaPagoBetween(usuario, "REJECTED", startOfYesterday, endOfYesterday);
        return rejectedPayments.size();
    }

    public double getTotalAmountLastWeek(Usuario usuario) {
    	
    	if (usuario == null) {
            throw new UserNotFoundException("El usuario no puede ser nulo");
        }
    	
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        List<Pagos> payments;

        try {
            payments = pagosRepository.findByUserAndFechaPagoAfter(usuario, sevenDaysAgo.atStartOfDay());
            
            // Verificar si el usuario tiene pagos en la última semana
            if (payments == null || payments.isEmpty()) {
            	 throw new IllegalArgumentException("El usuario no tiene pagos en la última semana.");
            }
        } catch (Exception e) {
            logger.error("Error al acceder a la base de datos para el usuario: {}", usuario.getId(), e);
            throw new IllegalArgumentException(e.getMessage());
        }
       
        // Sumar los totales de pagos en moneda local de los ultimos 7 dias
        double totalEnMonedaLocal = payments.stream().mapToDouble(Pagos::getTotalMLocal).sum();
        
        //Asigno el tipo de Moneda Local
        this.setMonedaLocal(payments.get(0).getMonedaLocal().trim());
        
        // Devolver el total en moneda local
        return totalEnMonedaLocal;
        
    }
    
 
	private double convertirMonedaAUsd(double totalAmountLastWeekLocal, String monedaLocal) {
    	
        CurrencyConversionResponseDTO response = currencyConversionService.convertCurrency(monedaLocal, "USD", totalAmountLastWeekLocal);
        
        if (response == null) {
            throw new IllegalStateException("CurrencyConversionResponseDTO is null");
        }
        
        return response.isSuccess() ? response.getResult() : 0.0; // Manejo de error en caso de falla en la conversión
    }
    
    

    public Usuario findUserById(Long userId) {
        return usuarioRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + userId));
    }
    
    public String getMonedaLocal() {
 		return monedaLocal;
 	}


 	public void setMonedaLocal(String monedaLocal) {
 		this.monedaLocal = monedaLocal;
 	}

}
