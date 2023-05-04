package com.soo.api.projeto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.soo.api.projeto.model.Historico;
import com.soo.api.projeto.repository.HistoricoRepository;

@Service
public class HistoricoService {
	
	@Autowired
	HistoricoRepository historicoRepository;

		// READ
		public List<Historico> getHistorico(Long idConta) {
		    return historicoRepository.findByIdConta(idConta);
		}
}
