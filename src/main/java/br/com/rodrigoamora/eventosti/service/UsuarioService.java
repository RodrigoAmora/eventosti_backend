package br.com.rodrigoamora.eventosti.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.rodrigoamora.eventosti.entity.Usuario;

public interface UsuarioService {

	public Usuario salvar(Usuario usuario);
	public Usuario editar(Usuario usuario);
	
	public void apagarUsuario(Usuario usuario);
	
	public Usuario buscarUsuarioPorId(Long id);
	public Usuario buscarUsuarioPorLogin(String login);
	public Optional<Usuario> buscarUsurioPorId(Long id);
	
	public Page<Usuario> listarTodos(int page, int size);

	public void trocarSenha(Usuario usuario);
	
	public Usuario getUsuarioLogado();
	
}
