package br.com.rodrigoamora.eventosti.service;

import br.com.rodrigoamora.eventosti.entity.Usuario;

public interface UsuarioService {

	public Usuario salvarUsuario(Usuario usuario);
	public void apagarUsuario(Usuario usuario);
	
	public Usuario buscarUsuarioPorId(Long id);
	
}
