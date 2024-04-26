package br.com.rodrigoamora.eventosti.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodrigoamora.eventosti.entity.Evento;
import br.com.rodrigoamora.eventosti.service.impl.EventoServiceImpl;

@RestController
@RequestMapping("/api/evento")
public class EventoApiController {

	@Autowired
	private EventoServiceImpl eventoService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Evento> buscarEventoPorId(@PathVariable(name = "id") Long id) {
		Optional<Evento> evento = this.eventoService.buscarEventoPorId(id);

		if (evento.isPresent()) {
			return ResponseEntity.ok(evento.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{nome}")
	public ResponseEntity<Page<Evento>> buscarEventoPorNome(@PathVariable(name = "nome") String nome,
														  @RequestParam(value = "page", required = false, defaultValue = "0") int page,
														  @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
		Page<Evento> eventos = this.eventoService.buscarEventoPorNome(nome, page, size);

		if (!eventos.isEmpty()) {
			return ResponseEntity.ok(eventos);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping
	public Page<Evento> listarTodos(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			   				        @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
		return this.eventoService.listarEventosAprovados(page, size);
	}
	
	@DeleteMapping(value = { "/{id}" })
	@PreAuthorize("hasRole('MODERATOR') || hasRole('ADMIN')")
	public HttpStatus apagarEventoPorId(@PathVariable(name = "id") Long id) {
		this.eventoService.apagarEventoPorId(id);
		return HttpStatus.OK;
	}
	
}
