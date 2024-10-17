package com.fraud.FraudPrevent.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fraud.FraudPrevent.model.Pagos;
import com.fraud.FraudPrevent.model.Usuario;

public interface PagosRepository extends JpaRepository<Pagos, Long> {

	/**
     * Encuentra el primer pago realizado por un usuario después de una fecha específica,
     * ordenado por la fecha de pago ascendente.
     * 
     * @param user el usuario que realizó el pago
     * @param fecha la fecha a partir de la cual buscar pagos
     * @return el primer pago que cumple con los criterios
     */
    Pagos findFirstByUserAndFechaPagoAfterOrderByFechaPagoAsc(Usuario user, LocalDateTime fecha);
    

    /**
     * Obtiene una lista de pagos realizados por un usuario dentro de un rango de fechas específico (última semana).
     * 
     * @param user el usuario que realizó los pagos
     * @param fechaInicio la fecha de inicio del rango
     * @param fechaFin la fecha de fin del rango
     * @return una lista de pagos que cumplen con los criterios
     */
    List<Pagos> findByUserAndFechaPagoBetween(Usuario user, LocalDate fechaInicio, LocalDate fechaFin);

    
    /**
     * Encuentra todos los pagos de un usuario con un estado específico entre dos fechas.
     * 
     * @param usuario el usuario que realizó los pagos
     * @param estadoDelPago el estado del pago que se está buscando
     * @param startOfYesterday inicio del rango de fecha
     * @param endOfYesterday fin del rango de fecha
     * @return una lista de pagos que cumplen con los criterios
     */
	List<Pagos> findByUserAndEstadoDelPagoAndFechaPagoBetween(Usuario usuario, String estadoDelPago,
			LocalDateTime startOfYesterday, LocalDateTime endOfYesterday);

	
	/**
     * Encuentra los pagos realizados por un usuario después de un día específico.
     * 
     * @param usuario el usuario que realizó los pagos
     * @param atStartOfDay la fecha y hora desde la cual buscar pagos
     * @return una lista de pagos que cumplen con los criterios
     */
	List<Pagos> findByUserAndFechaPagoAfter(Usuario usuario, LocalDateTime atStartOfDay);

}
