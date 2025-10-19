package br.com.rodrigoamora.eventosti.controller.api;

import java.util.Optional;

import br.com.rodrigoamora.eventosti.dto.request.EventoRequestDTO;
import br.com.rodrigoamora.eventosti.dto.response.EventoResponseDTO;
import br.com.rodrigoamora.eventosti.entity.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodrigoamora.eventosti.controller.api.doc.EventoApiDoc;
import br.com.rodrigoamora.eventosti.service.EventoService;

@RestController
@RequestMapping("/api/evento")
public class EventoApiController implements EventoApiDoc {

	@Autowired
	private EventoService eventoService;
	
	@Override
	@PostMapping
	public ResponseEntity<EventoResponseDTO> salvarEvento(@RequestBody EventoRequestDTO eventoRequestDTO) {
		var r = this.eventoService.salvarEvento(eventoRequestDTO);
		return ResponseEntity.ok(r);
	}
	
	@Override
	@GetMapping(value = { "/{id}" })
	public ResponseEntity<EventoResponseDTO> buscarEventoPorId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.eventoService.buscarEventoPeloId(id));
    }

	@Override
	@PutMapping(value = { "/{id}" })
	public ResponseEntity<EventoResponseDTO> editarEvento(@PathVariable(name = "id") Long id,
														  @RequestBody EventoRequestDTO eventoRequestDTO) {
		return ResponseEntity.ok(this.eventoService.editarEvento(id, eventoRequestDTO));
	}
	
	@Override
	@GetMapping("/buscarPorNome")
	public ResponseEntity<Page<EventoResponseDTO>> buscarEventoPorNome(@RequestParam(name = "nome") String nome,
																	  @RequestParam(value = "page", required = false, defaultValue = "0") int page,
																	  @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
		Page<EventoResponseDTO> eventos= this.eventoService.buscarEventoPorNome(nome, page, size);

		if (!eventos.isEmpty()) {
			return ResponseEntity.ok(eventos);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Override
	@GetMapping
	public Page<EventoResponseDTO> listarTodos(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
									@RequestParam(value = "size", required = false, defaultValue = "20") int size,
									@RequestParam(value = "order", required = false, defaultValue = "dataInicio") String order) {
		return this.eventoService.listarEventosAprovados(page, size, order);
	}
	
	@Override
	@DeleteMapping(value = { "/{id}" })
	@PreAuthorize("hasRole('MODERATOR') || hasRole('ADMIN')")
	public HttpStatus apagarEventoPorId(@PathVariable(name = "id") Long id) {
		var eventoEncontrado = this.eventoService.buscarEventoPorId(id);
		if (!eventoEncontrado.isPresent()) {
			return HttpStatus.NOT_FOUND;
		}
		
		this.eventoService.apagarEventoPorId(id);
		return HttpStatus.OK;
	}
	
	@Override
	@DeleteMapping
	@PreAuthorize("hasRole('MODERATOR') || hasRole('ADMIN')")
	public HttpStatus apagarTodosEvento() {
		this.eventoService.apagarTodosEvento();
		return HttpStatus.OK;
	}
	
}
