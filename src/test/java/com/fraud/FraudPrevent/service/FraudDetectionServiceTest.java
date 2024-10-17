package com.fraud.FraudPrevent.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fraud.FraudPrevent.dto.CurrencyConversionResponseDTO;
import com.fraud.FraudPrevent.dto.FraudMetricsDTO;
import com.fraud.FraudPrevent.excepciones.UserNotFoundException;
import com.fraud.FraudPrevent.model.Pagos;
import com.fraud.FraudPrevent.model.Usuario;
import com.fraud.FraudPrevent.repository.PagosRepository;
import com.fraud.FraudPrevent.repository.UsuarioRepository;

public class FraudDetectionServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PagosRepository pagosRepository;

    @InjectMocks
    private FraudDetectionService fraudDetectionService;

    @Mock
	private CurrencyConversionService currencyConversionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFraudMetrics_GralException() {
    	 Usuario usuario = new Usuario();
    	    usuario.setId(1L);
    	    usuario.setFechaCreacionCta(LocalDateTime.now().minusDays(4)); // Usuario nuevo

    	    Pagos pago = new Pagos();
    	    pago.setMonedaLocal("ARS");
    	    pago.setTotalMLocal(2500.0);
    	    pago.setEstadoDelPago("REJECTED");
    	    pago.setUser(usuario);
    	    pago.setFechaPago(LocalDateTime.now().minusDays(3));

    	    // Mockear las respuestas del repositorio
    	    when(pagosRepository.findByUserAndEstadoDelPagoAndFechaPagoBetween(eq(usuario), eq("REJECTED"), any(LocalDateTime.class), any(LocalDateTime.class)))
    	        .thenReturn(Collections.singletonList(pago));
    	    when(pagosRepository.findByUserAndFechaPagoAfter(any(Usuario.class), any(LocalDateTime.class)))
    	        .thenReturn(Collections.singletonList(pago));

    	    // Asegúrate de que cuando se llame al método se lanza la excepción.
    	    when(currencyConversionService.convertCurrency(eq("ARS"), eq("USD"), eq(2500.0)))
    	        .thenThrow(new IllegalStateException("CurrencyConversionResponseDTO is null")); // Simulamos un fallo en la conversión

    	    // Comprobación de excepción
    	    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
    	        fraudDetectionService.getFraudMetrics(usuario);
    	    });
    	    
    	    assertEquals("CurrencyConversionResponseDTO is null", exception.getMessage()); // Asegúrate que el mensaje coincida
    	}
}
    /*
    @Test
    public void testFindUserById_UserExists() {
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        Usuario result = fraudDetectionService.findUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test
    public void testFindUserById_UserNotFound() {
        Long userId = 2L;

        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            fraudDetectionService.findUserById(userId);
        });

        String expectedMessage = "Usuario no encontrado con ID: " + userId;       
        assertEquals(expectedMessage, exception.getMessage());
    }
    
    @Test
    public void testGetFraudMetrics_Success() {
        // Configuración del usuario
        Usuario usuario = new Usuario();
        usuario.setId(1L); // Asegúrate de establecer un ID
        usuario.setFechaCreacionCta(LocalDateTime.now().minusDays(4)); // Usuario nuevo

        Pagos pago = new Pagos();
        pago.setMonedaLocal("ARS");
        pago.setTotalMLocal(2500.0);
        pago.setEstadoDelPago("REJECTED");
        pago.setUser(usuario);
        pago.setFechaPago(LocalDateTime.now().minusDays(3));

        // Mockear las respuestas del repositorio
        when(pagosRepository.findByUserAndEstadoDelPagoAndFechaPagoBetween(eq(usuario), eq("REJECTED"), any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Collections.singletonList(pago));
        when(pagosRepository.findByUserAndFechaPagoAfter(any(Usuario.class), any(LocalDateTime.class)))
        .thenReturn(Arrays.asList(new Pagos())); // Retorna una lista de pagos simulados        
        when(currencyConversionService.convertCurrency(eq("ARS"), eq("USD"), eq(2500.0)))
            .thenReturn(new CurrencyConversionResponseDTO(true, 1.0));

        // Ejecutar el método a probar
        FraudMetricsDTO metrics = fraudDetectionService.getFraudMetrics(usuario);
        
        // Verificaciones
        assertNotNull(metrics);
        assertTrue(metrics.isIs_new_user());
        assertEquals(1, metrics.getQty_rejected_1d());
        assertEquals(1.0, metrics.getTotal_amt_7d(), 0.01); // Usar delta para precisión

        // Verifica que los métodos fueron llamados
        verify(pagosRepository, times(1)).findByUserAndEstadoDelPagoAndFechaPagoBetween(eq(usuario), eq("REJECTED"), any(LocalDateTime.class), any(LocalDateTime.class));
        verify(currencyConversionService).convertCurrency(eq("ARS"), eq("USD"), eq(2500.0));
    }


    
    @Test
    public void testGetFraudMetrics_NoPaymentsLastWeek_ThrowsException() {
        Usuario usuario = new Usuario();
        usuario.setFechaCreacionCta(LocalDateTime.now().minusDays(10)); // Usuario no nuevo

        // Simular que no hay pagos en la última semana
        when(pagosRepository.findByUserAndFechaPagoAfter(usuario, LocalDate.now().minusDays(7).atStartOfDay()))
            .thenReturn(Collections.emptyList());

        // Comprobación de excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fraudDetectionService.getFraudMetrics(usuario);
        });
        
        assertEquals("El usuario no tiene pagos en la última semana.", exception.getMessage());
    }

    
    
    @Test
    public void testGetFraudMetrics_UserNull_ThrowsException() {
        assertThrows(UserNotFoundException.class, () -> {
            fraudDetectionService.getFraudMetrics(null);
        });
    }
    

}
*/