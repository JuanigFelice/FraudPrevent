package com.fraud.FraudPrevent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fraud.FraudPrevent.model.Usuario;

//Interfaz que define el repositorio para la entidad Usuario.
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
