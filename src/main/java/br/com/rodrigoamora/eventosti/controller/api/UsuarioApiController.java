package br.com.rodrigoamora.eventosti.controller.api;

import java.util.Optional;

import br.com.rodrigoamora.eventosti.dto.request.UsuarioRequestDTO;
import br.com.rodrigoamora.eventosti.dto.response.UsuarioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.rodrigoamora.eventosti.controller.api.doc.UsuarioApiDoc;
import br.com.rodrigoamora.eventosti.entity.Usuario;
import br.com.rodrigoamora.eventosti.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioApiController implements UsuarioApiDoc {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	@PostMapping
	public ResponseEntity<UsuarioResponseDTO> salvarUsuario(@RequestBody UsuarioRequestDTO usuario) {
		return ResponseEntity.ok(this.usuarioService.salvar(usuario));
	}

	@Override
	@PatchMapping(value = { "/{id}" })
	public ResponseEntity<UsuarioResponseDTO> editarUsuario(@PathVariable(name = "id") Long id,
								 				 			@RequestBody UsuarioRequestDTO usuario) {
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
	public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable(name = "id") Long id) {
		Optional<Usuario> usuario = this.usuarioService.buscarUsurioPorId(id);

		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		}

		return ResponseEntity.notFound().build();
	}

	@Override
	@DeleteMapping(value = { "/{id}" })
	@PreAuthorize("hasRole('MODERATOR') || hasRole('ADMIN')")
	public HttpStatus apagarUsuarioPorId(@PathVariable(name = "id") Long id) {
		var eventoEncontrado = this.usuarioService.buscarUsurioPorId(id);
		if (!eventoEncontrado.isPresent()) {
			return HttpStatus.NOT_FOUND;
		}

		this.usuarioService.apagarUsuarioPorId(id);
		return HttpStatus.OK;
	}

}
