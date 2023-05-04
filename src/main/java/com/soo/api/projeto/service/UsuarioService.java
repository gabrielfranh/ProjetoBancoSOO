package com.soo.api.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.soo.api.projeto.model.Usuario;
import com.soo.api.projeto.repository.UsuarioRepository;
import java.util.List;

@Service
public class UsuarioService {
	@Autowired
	UsuarioRepository usuarioRepository;
	
	// CREATE 
	public Usuario createUsuario(Usuario usuario) {
	    return usuarioRepository.save(usuario);
	}
	
	public Usuario getUsuarioById(Long id) {
		var usuario = usuarioRepository.findById(id).orElse(null);
		
		if (usuario == null)
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "usuario nao encontrado"
					);
		else
			return usuario;
	}
	
	// UPDATE 
	public Usuario updateUsuario(Long usuarioId, Usuario usuario) {
		Usuario usuarioOld = usuarioRepository.findById(usuarioId).get();
		usuarioOld.setNome(usuario.getNome());
		usuarioOld.setNomeUsuario(usuario.getNomeUsuario());
		usuarioOld.setSenha(usuario.getSenha());
		usuarioOld.setDocumento(usuario.getDocumento());
	        
        return usuarioRepository.save(usuarioOld);                                
	}

	// READ
	public List<Usuario> getUsuarios() {
	    return usuarioRepository.findAll();
	}

	// DELETE
	public void deleteUsuario(Long usuarioId) {
		var usuario = usuarioRepository.findById(usuarioId).orElse(null);
		
		if (usuario == null)
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "usuario nao encontrado"
					);
		else
			usuarioRepository.deleteById(usuarioId);
	}
}
