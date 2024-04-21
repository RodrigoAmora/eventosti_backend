package br.com.rodrigoamora.eventosti.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.rodrigoamora.eventosti.entity.Usuario;
import br.com.rodrigoamora.eventosti.repository.UsuarioRepository;
import br.com.rodrigoamora.eventosti.service.UsuarioService;
import br.com.rodrigoamora.eventosti.validator.PasswordValidator;

public class UuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	
	public Usuario salvarUsuario(Usuario usuario) {
		String senhaUsuario = usuario.getSenha();
		usuario.setSenha(this.encryptPassword(senhaUsuario));
		
		return this.repository.save(usuario);
	}

	
	public void apagarUsuario(Usuario usuario) {
		this.repository.delete(usuario);
	}

	
	public Usuario buscarUsuarioPorId(Long id) {
		return this.repository.findById(id).get();
	}

	private String encryptPassword(String password) {
		return PasswordValidator.encryptPassword(password);
	}
	
}
