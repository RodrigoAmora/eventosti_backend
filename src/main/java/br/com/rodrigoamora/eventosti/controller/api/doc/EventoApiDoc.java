package br.com.rodrigoamora.eventosti.controller.api.doc;

import br.com.rodrigoamora.eventosti.dto.request.EventoRequestDTO;
import br.com.rodrigoamora.eventosti.dto.response.EventoResponseDTO;
import br.com.rodrigoamora.eventosti.entity.Evento;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Eventos")
public interface EventoApiDoc {

	@Operation(summary = "Cadastro de evento")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cadastro de evento", content = @Content(schema = @Schema(implementation = Evento.class))),
	})
	public ResponseEntity<EventoResponseDTO> salvarEvento(@RequestBody EventoRequestDTO eventoRequestDTO);

	@Operation(summary = "Edição de evento")
	public ResponseEntity<EventoResponseDTO> editarEvento(Long id, EventoRequestDTO eventoRequestDTO);

	@Operation(summary = "Busca de evento pelo id")
	public ResponseEntity<EventoResponseDTO> buscarEventoPorId(@PathVariable(name = "id") Long id);
	

	@Operation(summary = "Busca de evento pelo nome")
	public ResponseEntity<Page<EventoResponseDTO>> buscarEventoPorNome(@RequestParam(name = "nome") String nome,
																	  @RequestParam(value = "page", required = false, defaultValue = "0") int page,
																	  @RequestParam(value = "size", required = false, defaultValue = "20") int size);
	
	@Operation(summary = "Listagem de eventos")
	public Page<EventoResponseDTO> listarTodos(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
											  @RequestParam(value = "size", required = false, defaultValue = "20") int size,
											  @RequestParam(value = "order", required = false, defaultValue = "dataInicio") String order);
	
	@Operation(summary = "Apagar evento pelo id")
	public HttpStatus apagarEventoPorId(@PathVariable(name = "id") Long id);
	
	@Operation(summary = "Apagar todo os eventos")
	public HttpStatus apagarTodosEvento();
	
}
