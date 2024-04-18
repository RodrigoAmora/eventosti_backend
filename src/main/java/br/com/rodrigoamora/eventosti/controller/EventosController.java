package br.com.rodrigoamora.eventosti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.rodrigoamora.eventosti.entity.Evento;
import br.com.rodrigoamora.eventosti.service.impl.EventoServiceImpl;

@Controller
public class EventosController {

	@Autowired
	private EventoServiceImpl eventoService;
	
	@GetMapping("/evento/cadastrar")
	public String cadastrar(Model model){
		model.addAttribute("evento", new Evento()); 
		return "evento/cadastrar";
	}
	
	@PostMapping("/evento/salvar")
	public String salvar(@ModelAttribute Evento evento){
		this.eventoService.salvarEvento(evento);
		return "redirect:/";
	}
	
}
