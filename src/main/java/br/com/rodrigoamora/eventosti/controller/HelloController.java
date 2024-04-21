package br.com.rodrigoamora.eventosti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.rodrigoamora.eventosti.entity.Evento;
import br.com.rodrigoamora.eventosti.service.impl.EventoServiceImpl;

@Controller
public class HelloController {

	@Autowired
	private EventoServiceImpl eventoService;
	
	@GetMapping("/")
	public String index(Model model) {
		Page<Evento> eventos = this.eventoService.listarTodos(0, 20);
		model.addAttribute("eventos", eventos);
		return "index";
	}
	
}
