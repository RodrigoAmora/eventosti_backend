package br.com.rodrigoamora.eventosti.service;

import java.util.Optional;

import br.com.rodrigoamora.eventosti.dto.request.UsuarioRequestDTO;
import org.springframework.data.domain.Page;

import br.com.rodrigoamora.eventosti.entity.Usuario;

public interface UsuarioService {

	public Usuario salvar(UsuarioRequestDTO request);
	public Usuario editar(UsuarioRequestDTO request);
	
	public void apagarUsuario(Usuario usuario);
	public void apagarUsuarioPorId(Long id);
	
	public Usuario buscarUsuarioPorId(Long id);
	public Usuario buscarUsuarioPorLogin(String login);
	public Optional<Usuario> buscarUsurioPorId(Long id);
	
	public Page<Usuario> listarTodos(int page, int size);

	public void trocarSenha(Usuario usuario);
	
	public Usuario getUsuarioLogado();
	
}
