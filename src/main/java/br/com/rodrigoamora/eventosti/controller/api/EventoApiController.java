package br.com.rodrigoamora.eventosti.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodrigoamora.eventosti.entity.Evento;
import br.com.rodrigoamora.eventosti.service.impl.EventoServiceImpl;

@RestController
@RequestMapping("/evento")
public class EventoApiController {

	@Autowired
	EventoServiceImpl eventoService;
	
	@PostMapping
	public Evento salvarEvento(@RequestBody Evento evento) {
		return this.eventoService.salvarEvento(evento);
	}
	
}
