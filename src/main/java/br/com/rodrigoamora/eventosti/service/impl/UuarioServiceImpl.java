package br.com.rodrigoamora.eventosti.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodrigoamora.eventosti.entity.Usuario;
import br.com.rodrigoamora.eventosti.repository.UsuarioRepository;
import br.com.rodrigoamora.eventosti.service.UsuarioService;
import br.com.rodrigoamora.eventosti.validador.SenhaValidador;

@Component
public class UuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository userRepository;
	
//	@Autowired
//	private RoleRepository roleRepository;
	
	public Usuario salvarUsuario(Usuario usuario) {
		if (this.userRepository.findByEmail(usuario.getEmail()) != null) {
			usuario.setHasError("user.with.email.already.created");
			return usuario;
		}
		
		String senhaUsuario = usuario.getSenha();
		usuario.setSenha(this.encryptPassword(senhaUsuario));
		
		return this.userRepository.save(usuario);
	}

	public void apagarUsuario(Usuario usuario) {
		this.userRepository.delete(usuario);
	}

	
	public Usuario buscarUsuarioPorId(Long id) {
		return this.userRepository.findById(id).get();
	}

	private String encryptPassword(String password) {
		return SenhaValidador.encryptPassword(password);
	}
	
}
