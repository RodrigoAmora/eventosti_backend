package br.com.rodrigoamora.eventosti.service;

import java.util.List;
import java.util.Optional;
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
import br.com.rodrigoamora.eventosti.entity.TipoEvento;
import br.com.rodrigoamora.eventosti.repository.EventoRepository;

@Component
public class EventoService {

	@Autowired
	private EventoRepository eventoRepository;

	public Evento salvarEvento(Evento evento) {
		String site = this.verificarSite(evento.getSite());
		evento.setSite(site);
		
		if (evento.getDescricao() == null) {
			evento.setDescricao("");
		}
		
		if (evento.getTipoEvento() == null || evento.getTipoEvento().name().isEmpty()) {
			evento.setTipoEvento(TipoEvento.PRESENCIAL);
		}
		
		evento.setStatus(StatusEvento.EM_ESPERA);
		
		return this.eventoRepository.save(evento);
	}

	public Evento editarEvento(Evento evento) {
		String site = this.verificarSite(evento.getSite());
		evento.setSite(site);
		return this.eventoRepository.save(evento);
	}

	public Evento aprovarEvento(Long id) {
		Evento evento = this.eventoRepository.findById(id).get();
		evento.setStatus(StatusEvento.APROVADO);
		return this.eventoRepository.save(evento);
	}

	public void apagarEventoPorId(Long id) {
		this.eventoRepository.deleteById(id);
	}

	public void apagarTodosEvento() {
		this.eventoRepository.deleteAll();
	}

	public Page<Evento> listarEventosAprovados(int page, int size, String order) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, order);
		return this.eventoRepository.listarEventosAprovados(pageRequest);
	}

	public Page<Evento> listarEventosEmEspera(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
		return this.eventoRepository.listarEventosEmEspera(pageRequest);
	}

	public Optional<Evento> buscarEventoPorId(Long id) {
		return this.eventoRepository.buscarEventoPorId(id);
	}

	public Page<Evento> buscarEventoPorNome(String nome, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
		return this.eventoRepository.buscarEventoPorNome(nome, pageRequest);
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
	
	private String verificarSite(String siteEvento) {
		if (siteEvento == null) {
			return "";
		}
		if (!siteEvento.contains("www.") && !siteEvento.contains("http")) {
			return "http://"+siteEvento;
		}
		return siteEvento;
	}
	
}
