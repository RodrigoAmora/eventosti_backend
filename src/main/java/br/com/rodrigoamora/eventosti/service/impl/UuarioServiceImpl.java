package br.com.rodrigoamora.eventosti.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
		Role rolaAdmin = this.roleRepository.findByName(ERole.ROLE_ADMIN.name());
		usuario.getRoles().add(rolaAdmin);
		
		if (this.userRepository.findByLogin(usuario.getLogin()) != null) {
			usuario.setHasError("user.with.email.already.created");
			return usuario;
		}
		
		String senhaUsuario = usuario.getPassword();
		usuario.setPassword(this.encryptPassword(senhaUsuario));
		
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

	public Usuario buscarUsuarioPorLogin(String login) {
		return this.userRepository.findByLogin(login);
	}
	
	public void trocarSenha(Usuario usuario) {
		this.userRepository.save(usuario);
	}
	
	public Usuario getUsuarioLogado() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String nome;
		if (principal instanceof UserDetails) {
		    nome = ((UserDetails)principal).getUsername();
		} else {
		    nome = principal.toString();
		}
		
		return this.buscarUsuarioPorLogin(nome);
	}
	
	private String encryptPassword(String password) {
		return SenhaValidador.encryptPassword(password);
	}
	
}
