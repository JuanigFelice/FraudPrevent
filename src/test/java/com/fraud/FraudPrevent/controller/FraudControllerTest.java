package com.fraud.FraudPrevent.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fraud.FraudPrevent.dto.FraudMetricsDTO;
import com.fraud.FraudPrevent.excepciones.UserNotFoundException;
import com.fraud.FraudPrevent.model.Usuario;
import com.fraud.FraudPrevent.service.FraudDetectionService;


@WebMvcTest(FraudController.class)
public class FraudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean 
    private FraudDetectionService fraudDetectionService;

    @InjectMocks
    private FraudController fraudController;
    
    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @BeforeEach
    public void setUp() {
        // Inicializar mocks
    }

    @Test
    // Carga de usuario y contraseña desde propiedades
    @WithMockUser(username = "${spring.security.user.name}", password = "${spring.security.user.password}")
    public void testCheckFraudRisk_UserFound() throws Exception {
        
    	Long userId = 1L;
        Usuario mockUsuario = new Usuario(); // Simula un usuario
        FraudMetricsDTO metricsDTO = new FraudMetricsDTO(true, 0, 100.0);
      
        // Configurar el mock para que devuelva el usuario simulado
        when(fraudDetectionService.findUserById(userId)).thenReturn(mockUsuario);
        
        // Configurar el mock para que devuelva las métricas de fraude
        when(fraudDetectionService.getFraudMetrics(mockUsuario)).thenReturn(metricsDTO);


        mockMvc.perform(get("/api/fraud/checkFraudRisk?userId=" + userId))
            .andExpect(status().isOk()) // Se espera el código 200
            .andExpect(jsonPath("$.is_new_user").value(true));
    }

    @Test
    // Carga de usuario y contraseña desde propiedades
    @WithMockUser(username = "${spring.security.user.name}", password = "${spring.security.user.password}")
    public void testCheckFraudRisk_UserNotFound() throws Exception {
        Long userId = 2L;

        when(fraudDetectionService.getFraudMetrics(any())).thenThrow(new UserNotFoundException("Usuario no encontrado con ID: " + userId));

        mockMvc.perform(get("/api/fraud/checkFraudRisk?userId=" + userId))
            .andExpect(status().isNotFound());
    }
}
