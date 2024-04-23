package br.com.rodrigoamora.eventosti.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
		Role rolaAdmin = this.roleRepository.findByName(ERole.ROLE_USER.name());
		usuario.getRoles().add(rolaAdmin);
		
		if (this.userRepository.findByEmail(usuario.getEmail()) != null) {
			usuario.setHasError("user.with.email.already.created");
			return usuario;
		}
		
		String senhaUsuario = usuario.getSenha();
		usuario.setSenha(this.encryptPassword(senhaUsuario));
		
		return this.userRepository.save(usuario);
	}

	@Override
	public Page<Usuario> listarTodos(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
		return this.userRepository.listarTodos(pageRequest);
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
