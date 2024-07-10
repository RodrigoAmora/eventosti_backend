package br.com.rodrigoamora.eventosti.service;

import org.springframework.data.domain.Page;

import br.com.rodrigoamora.eventosti.entity.Usuario;

public interface UsuarioService {

	public Usuario salvarUsuario(Usuario usuario);
	public void apagarUsuario(Usuario usuario);
	
	public Usuario buscarUsuarioPorId(Long id);
	public Usuario buscarUsuarioPorLogin(String login);
	
	public Page<Usuario> listarTodos(int page, int size);

	public void trocarSenha(Usuario usuario);
	
	public Usuario getUsuarioLogado();
	
}
