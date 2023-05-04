package com.soo.api.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soo.api.projeto.model.Historico;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Long>{
	public List<Historico> findByIdConta(Long idConta);
}
