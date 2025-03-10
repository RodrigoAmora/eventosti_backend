package br.com.rodrigoamora.eventosti.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.rodrigoamora.eventosti.entity.Evento;
import br.com.rodrigoamora.eventosti.service.impl.EventoServiceImpl;

@Controller
public class HelloController {

	@Autowired
	private EventoServiceImpl eventoService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model,
		      			@RequestParam("page") Optional<Integer> page,
		      			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

		Page<Evento> eventos = this.eventoService.listarEventosAprovados(currentPage-1, pageSize);
		model = this.eventoService.setModel(model, eventos);
		
		return "index";
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String download() {
		return "download";
	}
}
