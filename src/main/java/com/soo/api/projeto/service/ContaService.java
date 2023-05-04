package com.soo.api.projeto.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.soo.api.projeto.repository.ContaRepository;
import com.soo.api.projeto.repository.HistoricoRepository;
//import com.soo.api.projeto.repository.ContaRepository;
import com.soo.api.projeto.repository.UsuarioRepository;
import com.soo.api.projeto.model.Conta;
import com.soo.api.projeto.model.Historico;
import com.soo.api.projeto.model.Usuario;

@Service
public class ContaService {
	@Autowired
	ContaRepository contaRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	HistoricoRepository historicoRepository;
	
	// CREATE 
	public Conta createConta(Conta conta) {
		Usuario usuario = usuarioRepository.findById(conta.getIdUsuario()).orElse(null);
		
		if (usuario == null)
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "usuario nao encontrado"
					);
		
	    return contaRepository.save(conta);
	}
	
	public Conta getContaById(Long contaId, Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
		
		if (usuario == null)
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "usuario nao encontrado"
					);
		
		var conta = contaRepository.findById(contaId).orElse(null);
		
		if (conta != null && conta.getIdUsuario() == idUsuario)
			return conta;
		else
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "conta nao encontrada"
					);
	}
	
	// UPDATE 
	public Conta updateConta(Long contaId, Long idUsuario, Conta conta) {
		
		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
		
		if (usuario == null)
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "usuario nao encontrado"
					);
		
		Conta contaOld = contaRepository.findById(contaId).get();
		
		if (contaOld == null || contaOld.getIdUsuario() != idUsuario)
			return null;
		
		contaOld.setDataCriacao(conta.getDataCriacao());
		contaOld.setSaldo(conta.getSaldo());
	        
        return contaRepository.save(contaOld);                                
	};

	// READ
	public List<Conta> getContas(Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
		
		if (usuario == null)
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "usuario nao encontrado"
					);
		
	    return contaRepository.findByIdUsuario(idUsuario);
	}

	// DELETE
	public void deleteConta(Long contaId, Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
		
		if (usuario == null)
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "usuario nao encontrado"
					);
		
		var conta = contaRepository.findById(contaId).orElse(null);
		
		if (conta == null || conta.getIdUsuario() != idUsuario)
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "conta nao encontrada"
					);
		
		contaRepository.deleteById(contaId);
	}
	
	// SACAR
	public void sacar(Long contaId, Long idUsuario, Double valor) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
		
		if (usuario == null)
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "usuario nao encontrado"
					);
		
		var conta = contaRepository.findById(contaId).orElse(null);
		
		if (conta == null || conta.getIdUsuario() != idUsuario)
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "conta nao encontrada"
					);
		
		if (conta.getSaldo() < valor)
			throw new ResponseStatusException (
					HttpStatus.BAD_REQUEST, "Saldo da conta insuficiente para saque"
					);
		
		conta.setSaldo(conta.getSaldo() - valor);
		
		contaRepository.save(conta);
		
		Historico historico = new Historico();
		historico.setDataMovimento(new Date());
		historico.setValorMovimentado(valor);
		historico.setIdConta(contaId);
		historico.setTipo("saque");
		
		historicoRepository.save(historico);
	}
	
	// DEPOSITAR
	
	public void depositar(Long contaId, Long idUsuario, Double valor) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
		
		if (usuario == null)
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "usuario nao encontrado"
					);
		
		var conta = contaRepository.findById(contaId).orElse(null);
		
		if (conta == null || conta.getIdUsuario() != idUsuario)
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "conta nao encontrada"
					);
		
		conta.setSaldo(conta.getSaldo() + valor);
		
		contaRepository.save(conta);
		
		Historico historico = new Historico();
		historico.setDataMovimento(new Date());
		historico.setValorMovimentado(valor);
		historico.setIdConta(contaId);
		historico.setTipo("deposito");
		
		historicoRepository.save(historico);
	}
	
	// CONSULTAR SALDO CONTA
	
	public Double getSaldo(Long contaId, Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
		
		if (usuario == null)
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "usuario nao encontrado"
					);
		
		var conta = contaRepository.findById(contaId).orElse(null);
		
		if (conta == null || conta.getIdUsuario() != idUsuario)
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "conta nao encontrada"
					);
		
		return conta.getSaldo();
	}
}
