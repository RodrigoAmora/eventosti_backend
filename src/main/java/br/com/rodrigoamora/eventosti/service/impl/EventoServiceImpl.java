package br.com.rodrigoamora.eventosti.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import br.com.rodrigoamora.eventosti.entity.Evento;
import br.com.rodrigoamora.eventosti.entity.StatusEvento;
import br.com.rodrigoamora.eventosti.repository.EventoRepository;
import br.com.rodrigoamora.eventosti.service.EventoService;

@Component
public class EventoServiceImpl implements EventoService {

	@Autowired
	private EventoRepository eventoRepository;
	
	@Override
	public Evento salvarEvento(Evento evento) {
		evento.setStatus(StatusEvento.EM_ESPERA);
		return this.eventoRepository.save(evento);
	}

	@Override
	public void apagarEventoPorId(Long id) {}

	@Override
	public Page<Evento> listarTodos(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
		return this.eventoRepository.listarTodos(pageRequest);
	}

	@Override
	public Page<Evento> buscarEventoPorNome(String nome) {
		return null;
	}

	public Model setModel(Model model, Page<Evento> eventos) {
		int totalPages = eventos.getTotalPages();
		if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

		model.addAttribute("eventos", eventos);
        
		return model;
	}
	
}
