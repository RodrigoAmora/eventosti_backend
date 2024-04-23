package br.com.rodrigoamora.eventosti.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodrigoamora.eventosti.entity.Usuario;
import br.com.rodrigoamora.eventosti.service.impl.UuarioServiceImpl;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioApiController {

	@Autowired
	private UuarioServiceImpl usuarioService;
	
	@PostMapping
	public Usuario salvarEvento(@RequestBody Usuario usuario) {
		return this.usuarioService.salvarUsuario(usuario);
	}
	
	@GetMapping
	public Page<Usuario> listarTodos(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			   				  		 @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
		return this.usuarioService.listarTodos(page, size);
	}
	
}
