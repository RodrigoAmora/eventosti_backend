package br.com.rodrigoamora.eventosti.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.rodrigoamora.eventosti.entity.Evento;
import br.com.rodrigoamora.eventosti.entity.dto.EventoDTO;
import br.com.rodrigoamora.eventosti.service.impl.EventoServiceImpl;
import jakarta.validation.Valid;

@Controller
public class EventoController {

	@Autowired
	private EventoServiceImpl eventoService;
	
	@GetMapping("/evento/cadastrar")
	public String cadastrar(Model model) {
		model.addAttribute("evento", new EventoDTO());
		return "evento/enviar_evento";
	}
	
	@PostMapping("/evento/cadastrar")
	public String salvar(@ModelAttribute("evento") @Valid EventoDTO evento, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "evento/enviar_evento";
		}
		
		Evento event = evento.converterParaEvento();
		this.eventoService.salvarEvento(event);
		
		return "redirect:/evento/cadastrar?result=success";
	}
	
	@GetMapping("/evento/buscar")
	public String buscarEventoPorNome(@RequestParam("nomeEvento") String nome,
									  Model model,
			  						  @RequestParam("page") Optional<Integer> page,
			  						  @RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		
		Page<Evento> eventos = this.eventoService.buscarEventoPorNome(nome, currentPage-1, pageSize);
		model = this.eventoService.setModel(model, eventos);
		
		if (eventos.isEmpty()) {
			return "not_found";
		}
		
		return "index";
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
	
	@GetMapping("/verEvento")
	public String verEvento(Model model,
			  				@RequestParam("id") Long id) {
		Optional<Evento> evento = this.eventoService.buscarEventoPorId(id);
		if (!evento.isPresent()) {
			return "not_found";
		}
		model.addAttribute("evento", evento.get());
		return "evento/detalhes_evento";
	}
	
	@GetMapping("/evento/{id}/aprovar")
	@PreAuthorize("hasRole('MODERATOR') || hasRole('ADMIN')")
	public String aprovar(@PathVariable Long id) {
		this.eventoService.aprovarEvento(id);
		return "redirect:/evento/aprovar";
	}
	
	@GetMapping("/evento/{id}/apagar")
	@PreAuthorize("hasRole('MODERATOR') || hasRole('ADMIN')")
	public String apagar(@PathVariable Long id) {
		this.eventoService.apagarEventoPorId(id);
		return "redirect:/evento/aprovar";
	}
	
}
