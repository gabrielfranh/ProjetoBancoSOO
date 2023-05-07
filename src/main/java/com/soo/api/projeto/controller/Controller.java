package com.soo.api.projeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soo.api.projeto.model.*;
import com.soo.api.projeto.service.*;

@CrossOrigin(origins = "http://127.0.0.1:8081")
@RestController
@RequestMapping("/api")
public class Controller {
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ContaService contaService;
	
	@Autowired
	HistoricoService historicoService;

	// PARTE DO USUARIO
	
	// Criar usuario
	@RequestMapping(value = "/usuarios", method = RequestMethod.POST)
	public Usuario createUsuario(@RequestBody Usuario usuario) {
		return usuarioService.createUsuario(usuario);
	}

	// Retorna usuarios
	@RequestMapping(value = "/usuarios", method = RequestMethod.GET)
	public List<Usuario> getUsuarios() {
		return usuarioService.getUsuarios();
	}


	// Retorna usuario por nomeUsuario
	@RequestMapping(value = "/usuarios/login/{nomeUsuario}", method = RequestMethod.GET)
	public Usuario getUsuarioByNomeUsuario(@PathVariable(value = "nomeUsuario") String nomeUsuario) {
		return usuarioService.getUsuarioByNomeUsuario(nomeUsuario);
	}

	// Retorna usuario por id usuario
	@RequestMapping(value = "/usuarios/{usuarioId}", method = RequestMethod.GET)
	public Usuario getUsuarioById(@PathVariable(value = "usuarioId") Long id) {
		return usuarioService.getUsuarioById(id);
	}
	
	// Retorna usuario por id usuario
	@RequestMapping(value = "/usuarios/{usuarioId}", method = RequestMethod.GET)
	public Usuario getUsuarioByUser(@PathVariable(value = "usuarioId") Long id) {
		return usuarioService.getUsuarioById(id);
	}

	// Altera usuario
	 @RequestMapping(value="/usuarios/{usuarioId}", method=RequestMethod.PUT)
	 public Usuario updateUsuario(@PathVariable(value="usuarioId") Long id, @RequestBody Usuario usuario) {
	 return usuarioService.updateUsuario(id, usuario);
	 }

	 // Deletar usuario
	@RequestMapping(value = "/usuarios/{usuarioId}", method = RequestMethod.DELETE)
	public void deleteUsuario(@PathVariable(value = "usuarioId") Long id) {
		usuarioService.deleteUsuario(id);
	}
	
	// PARTE DA CONTA
	
	// Criar conta
	@RequestMapping(value = "/contas", method = RequestMethod.POST)
	public Conta createConta(@RequestBody Conta conta) {
		return contaService.createConta(conta);
	}

	// Retorna contas por usuario id
	@RequestMapping(value = "/contas", method = RequestMethod.GET)
	public List<Conta> getContas(@RequestParam("idUsuario") Long idUsuario) {
		return contaService.getContas(idUsuario);
	}

	// Retorna conta por contaId por usuarioId
	@RequestMapping(value = "/contas/{contaId}", method = RequestMethod.GET)
	public Conta getConta(@PathVariable(value = "contaId") Long contaId, @RequestParam("idUsuario") Long idUsuario) {
		return contaService.getContaById(contaId, idUsuario);
	}

	// Alterar conta
	 @RequestMapping(value="/contas/{contaId}", method=RequestMethod.PUT)
	 public Conta updateConta(@PathVariable(value="contaId") Long contaId,@RequestParam("idUsuario") Long idUsuario, @RequestBody Conta conta) {
	 return contaService.updateConta(contaId, idUsuario, conta);
	 }

	 // Deletar conta
	@RequestMapping(value = "/contas/{contaId}", method = RequestMethod.DELETE)
	public void deleteConta(@PathVariable(value = "contaId") Long contaId, @RequestParam("idUsuario") Long idUsuario) {
		contaService.deleteConta(contaId, idUsuario);
	}
	
	// Sacar de conta
	@RequestMapping(value = "/contas/sacar/{contaId}", method = RequestMethod.POST)
	public void sacar(@PathVariable(value="contaId") Long contaId,@RequestParam("idUsuario") Long idUsuario, @RequestParam("valor") Double valor) {
		contaService.sacar(contaId, idUsuario, valor);
	}
	
	// Depositar na conta
	@RequestMapping(value = "/contas/depositar/{contaId}", method = RequestMethod.POST)
	public void depositar(@PathVariable(value="contaId") Long contaId,@RequestParam("idUsuario") Long idUsuario, @RequestParam("valor") Double valor) {
		contaService.depositar(contaId, idUsuario, valor);
	}
	
	// Retorna saldo de conta
	@RequestMapping(value = "/contas/saldo/{contaId}", method = RequestMethod.GET)
	public Double getSaldo(@PathVariable(value = "contaId") Long contaId, @RequestParam("idUsuario") Long idUsuario) {
		return contaService.getSaldo(contaId, idUsuario);
	}
	
	// PARTE DO HISTORICO
	
	// Retorna historico por id conta
		@RequestMapping(value = "/historico/{idConta}", method = RequestMethod.GET)
		public List<Historico> getHistorico(@PathVariable(value = "idConta") Long idConta) {
			return historicoService.getHistorico(idConta);
		}
}
