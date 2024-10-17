package com.fraud.FraudPrevent.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "pais")
    private String pais;

    @Column(name = "fecha_creacion_cta", nullable = false)
    private LocalDateTime fechaCreacionCta;

    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public LocalDateTime getFechaCreacionCta() {
		return fechaCreacionCta;
	}

	public void setFechaCreacionCta(LocalDateTime fechaCreacionCta) {
		this.fechaCreacionCta = fechaCreacionCta;
	}

   
   

}
