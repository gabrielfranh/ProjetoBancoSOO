package com.soo.api.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soo.api.projeto.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
	List<Conta> findByIdUsuario(Long id_usuario);
}
