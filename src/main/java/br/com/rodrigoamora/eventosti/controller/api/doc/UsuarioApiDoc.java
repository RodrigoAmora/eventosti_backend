package br.com.rodrigoamora.eventosti.controller.api.doc;

import br.com.rodrigoamora.eventosti.dto.request.UsuarioRequestDTO;
import br.com.rodrigoamora.eventosti.dto.response.UsuarioResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.rodrigoamora.eventosti.entity.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Endpoints de usários")
public interface UsuarioApiDoc {

	@Operation(summary = "Cadastro de usário")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cadastro de evento", content = @Content(schema = @Schema(implementation = Usuario.class))),
	})
	public ResponseEntity<UsuarioResponseDTO> salvarUsuario(@RequestBody UsuarioRequestDTO usuario);
	
	@Operation(summary = "Edição de usário")
	public ResponseEntity<Usuario> editarUsuario(@PathVariable(name = "id") Long id,
			 									 @RequestBody Usuario usuario);
	
	@Operation(summary = "Listagem de usários")
	public Page<Usuario> listarTodos(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
		  		 					 @RequestParam(value = "size", required = false, defaultValue = "20") int size);
	
	
	@Operation(summary = "Buscar usário pelo id")
	public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable(name = "id") Long id);

	@Operation(summary = "Apagar usário pelo id")
	public HttpStatus apagarUsuarioPorId(@PathVariable(name = "id") Long id);
}
