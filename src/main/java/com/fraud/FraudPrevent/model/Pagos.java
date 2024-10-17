package com.fraud.FraudPrevent.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagos")
public class Pagos {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private Usuario user;

    @Column(name = "pais")
    private String pais;

    @Column(name = "moneda_local", length = 3, nullable = false)
    private String monedaLocal;

    @Column(name = "total_moneda_local", nullable = false)
    private Double totalMLocal;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Column(name = "estado_pago", length = 50, nullable = false)
    private String estadoDelPago;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getMonedaLocal() {
		return monedaLocal;
	}

	public void setMonedaLocal(String monedaLocal) {
		this.monedaLocal = monedaLocal;
	}

	public Double getTotalMLocal() {
		return totalMLocal;
	}

	public void setTotalMLocal(Double totalMLocal) {
		this.totalMLocal = totalMLocal;
	}

	public LocalDateTime getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDateTime fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getEstadoDelPago() {
		return estadoDelPago;
	}

	public void setEstadoDelPago(String estadoDelPago) {
		this.estadoDelPago = estadoDelPago;
	}

    
}
