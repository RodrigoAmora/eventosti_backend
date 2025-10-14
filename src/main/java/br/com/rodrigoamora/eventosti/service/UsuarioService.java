package br.com.rodrigoamora.eventosti.service;

import br.com.rodrigoamora.eventosti.dto.request.UsuarioRequestDTO;
import br.com.rodrigoamora.eventosti.dto.response.UsuarioResponseDTO;
import br.com.rodrigoamora.eventosti.entity.Usuario;
import br.com.rodrigoamora.eventosti.entity.role.ERole;
import br.com.rodrigoamora.eventosti.entity.role.Role;
import br.com.rodrigoamora.eventosti.repository.RoleRepository;
import br.com.rodrigoamora.eventosti.repository.UsuarioRepository;
import br.com.rodrigoamora.eventosti.validador.SenhaValidador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioService {

	@Autowired
	private UsuarioRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public UsuarioResponseDTO salvar(UsuarioRequestDTO request) {
		var usuario = this.checarUsuario(request);
		if (!usuario.getHasError().isEmpty()) {
			return null;
		}

		usuario.setLogin(request.login());
		usuario.setNome(request.nome());

		String senhaUsuario = request.password();
		usuario.setPassword(this.encryptPassword(senhaUsuario));

		usuario = this.userRepository.save(usuario);

		UsuarioResponseDTO response = new UsuarioResponseDTO(usuario.getId(), usuario.getNome(),
															 usuario.getLogin(), usuario.getHasError());

		return response;
	}

	public Usuario editar(UsuarioRequestDTO request) {
		var usuarioChecado = this.checarUsuario(request);
		if (!usuarioChecado.getHasError().isEmpty()) {
			return usuarioChecado;
		}
		
		return this.userRepository.save(usuarioChecado);
	}

	public Page<Usuario> listarTodos(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
		return this.userRepository.listarTodos(pageRequest);
	}

	public void apagarUsuario(Usuario usuario) {
		this.userRepository.delete(usuario);
	}

	public void apagarUsuarioPorId(Long id) {
		this.userRepository.deleteById(id);
	}

	public Optional<Usuario> buscarUsurioPorId(Long id) {
		return this.userRepository.findById(id);
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
		String nome;
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		    nome = ((UserDetails)principal).getUsername();
		} else {
		    nome = principal.toString();
		}
		
		return this.buscarUsuarioPorLogin(nome);
	}
	
	private Usuario checarUsuario(UsuarioRequestDTO request) {
		Usuario usuario = new Usuario();

		Role rolaAdmin = this.roleRepository.findByName(ERole.ROLE_ADMIN.name());
		usuario.getRoles().add(rolaAdmin);
		
		if (this.userRepository.findByLogin(request.login()) != null) {
			usuario.setHasError("user.with.email.already.created");
			return usuario;
		}

		usuario.setHasError("");

		return usuario;
	}
	
	private String encryptPassword(String password) {
		return SenhaValidador.encryptPassword(password);
	}
	
}
