package br.com.rodrigoamora.eventosti.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodrigoamora.eventosti.entity.Usuario;
import br.com.rodrigoamora.eventosti.entity.role.ERole;
import br.com.rodrigoamora.eventosti.entity.role.Role;
import br.com.rodrigoamora.eventosti.repository.RoleRepository;
import br.com.rodrigoamora.eventosti.repository.UsuarioRepository;
import br.com.rodrigoamora.eventosti.service.UsuarioService;
import br.com.rodrigoamora.eventosti.validador.SenhaValidador;

@Component
public class UuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public Usuario salvarUsuario(Usuario usuario) {
//		List<Role> roles = new ArrayList<>();
//		roles.add(this.roleRepository.findByName(ERole.ROLE_USER.name()));
//		
//		usuario.setRoles(roles);
//		
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
