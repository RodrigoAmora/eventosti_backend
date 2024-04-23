package br.com.rodrigoamora.eventosti.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping
	public Evento salvarEvento(@RequestBody Evento evento) {
		return this.eventoService.salvarEvento(evento);
	}
	
	@GetMapping
	public Page<Evento> listarTodos(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			   				  @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
		return this.eventoService.listarEventosAprovados(page, size);
	}
}
