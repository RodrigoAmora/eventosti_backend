package br.com.rodrigoamora.eventosti.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodrigoamora.eventosti.entity.Usuario;
import br.com.rodrigoamora.eventosti.service.impl.UuarioServiceImpl;

@RestController
@RequestMapping("/usuario")
public class UsuarioApiController {

	@Autowired
	private UuarioServiceImpl usuarioService;
	
	@PostMapping
	public Usuario salvarEvento(@RequestBody Usuario usuario) {
		return this.usuarioService.salvarUsuario(usuario);
	}
	
}
