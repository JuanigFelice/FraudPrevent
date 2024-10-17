package com.fraud.FraudPrevent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	
	@Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;
    
	public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
	        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
	    }
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
            		 // Permitir acceso a las URL de Swagger
                    .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui/index.html"
                    ).permitAll() // Permitir acceso sin autenticación
                    
                    // Configura la ruta de la App
                    .requestMatchers("/api/fraud/checkFraudRisk/{userId}").hasRole("USER")
                    .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .exceptionHandling(exceptionHandling -> exceptionHandling
            .authenticationEntryPoint(this.customAuthenticationEntryPoint));         

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
    	
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        	authenticationManagerBuilder.inMemoryAuthentication()
            .withUser(username).password(passwordEncoder().encode(password)).roles("USER");
            
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
