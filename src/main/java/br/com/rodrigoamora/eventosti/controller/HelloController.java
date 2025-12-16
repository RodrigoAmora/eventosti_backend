package br.com.rodrigoamora.eventosti.controller;

import br.com.rodrigoamora.eventosti.dto.response.EventoResponseDTO;
import br.com.rodrigoamora.eventosti.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class HelloController {

	@Autowired
	private EventoService eventoService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model,
		      			@RequestParam("page") Optional<Integer> page,
		      			@RequestParam("size") Optional<Integer> size,
		      			@RequestParam(value = "order", required = false, defaultValue = "dataInicio") String order) {
		int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

		Page<EventoResponseDTO> eventos = this.eventoService.listarEventosAprovados(currentPage-1, pageSize, order);
		model = this.eventoService.setModel(model, eventos);
		
		return "index";
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String download() {
		return "download";
	}

	@RequestMapping(value = "/wifi", method = RequestMethod.GET)
	public String wiFi() {
		return "video";
	}

}
