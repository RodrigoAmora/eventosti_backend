package br.com.rodrigoamora.eventosti.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.rodrigoamora.eventosti.entity.Evento;
import br.com.rodrigoamora.eventosti.service.impl.EventoServiceImpl;

@Controller
public class EventoController {

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
	
	@GetMapping("/evento/aprovar")
	@PreAuthorize("hasRole('MODERATOR') || hasRole('ADMIN')")
	public String aprovar(Model model,
  						  @RequestParam("page") Optional<Integer> page,
  						  @RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		
		Page<Evento> eventos = this.eventoService.listarEventosEmEspera(currentPage-1, pageSize);
		model = this.eventoService.setModel(model, eventos);
		
		return "admin/eventos_em_espera";
	}
	
	@GetMapping("/evento/{id}/aprovar")
	@PreAuthorize("hasRole('MODERATOR') || hasRole('ADMIN')")
	public String aprovar(@PathVariable Long id){
		this.eventoService.aprovarEvento(id);
		return "redirect:/evento/aprovar";
	}
	
}
