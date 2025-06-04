package br.com.rodrigoamora.eventosti.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodrigoamora.eventosti.controller.api.doc.UsuarioApiDoc;
import br.com.rodrigoamora.eventosti.entity.Usuario;
import br.com.rodrigoamora.eventosti.service.impl.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioApiController implements UsuarioApiDoc {

	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Override
	@PostMapping
	public ResponseEntity<Usuario> salvarUsuario(@RequestBody Usuario usuario) {
		return ResponseEntity.ok(this.usuarioService.salvar(usuario));
	}
	
	@Override
	@PatchMapping(value = { "/{id}" })
	public ResponseEntity<Usuario> editarUsuario(@PathVariable(name = "id") Long id,
								 				 @RequestBody Usuario usuario) {
		Optional<Usuario> usuarioDB = this.usuarioService.buscarUsurioPorId(id);

		if (usuarioDB.isPresent()) {
			return ResponseEntity.ok(this.usuarioService.editar(usuario));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Override
	@GetMapping
	public Page<Usuario> listarTodos(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			   				  		 @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
		return this.usuarioService.listarTodos(page, size);
	}
	
	@Override
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscarEventoPorId(@PathVariable(name = "id") Long id) {
		Optional<Usuario> usuario = this.usuarioService.buscarUsurioPorId(id);

		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
