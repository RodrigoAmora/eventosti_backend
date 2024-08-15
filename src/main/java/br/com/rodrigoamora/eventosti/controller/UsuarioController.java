package br.com.rodrigoamora.eventosti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.rodrigoamora.eventosti.entity.Usuario;
import br.com.rodrigoamora.eventosti.service.impl.UsuarioServiceImpl;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@PostMapping("/usuario/{id}/trocarSenha")
	@PreAuthorize("hasRole('MODERATOR') || hasRole('ADMIN')")
	public String trocarSenha(Model model,
							  @RequestParam("id") Long id) {
		String senha = model.getAttribute("senha").toString();
		
		Usuario usuario = this.usuarioService.buscarUsuarioPorId(id);
		usuario.setPassword(senha);
		
		this.usuarioService.trocarSenha(usuario);
		
		return "evento/enviar_evento";
	}
	
	@GetMapping("/usuario/trocarSenha")
	public String aa(Model model) {
		Usuario usuarioLogado = this.usuarioService.getUsuarioLogado();
		model.addAttribute("usuarioId", usuarioLogado.getId());
		return "admin/usuario/trocar_senha";
	}
	
}
