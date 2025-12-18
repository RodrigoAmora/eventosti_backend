package br.com.rodrigoamora.eventosti.service;

import br.com.rodrigoamora.eventosti.dto.request.EventoRequestDTO;
import br.com.rodrigoamora.eventosti.dto.response.EventoResponseDTO;
import br.com.rodrigoamora.eventosti.entity.Evento;
import br.com.rodrigoamora.eventosti.entity.StatusEvento;
import br.com.rodrigoamora.eventosti.entity.TipoEvento;
import br.com.rodrigoamora.eventosti.exception.EventoNotFoundException;
import br.com.rodrigoamora.eventosti.mapper.EventoMapper;
import br.com.rodrigoamora.eventosti.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EventoService {

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private EventoMapper eventoMapper;

	public EventoResponseDTO salvarEvento(EventoRequestDTO eventoRequestDTO) {
		String site = this.verificarSite(eventoRequestDTO.site());

		var evento = eventoMapper.toEntity(eventoRequestDTO);
		evento.setSite(site);
		
		if (evento.getDescricao() == null) {
			evento.setDescricao("");
		}
		
		if (evento.getTipoEvento() == null || evento.getTipoEvento().name().isEmpty()) {
			evento.setTipoEvento(TipoEvento.PRESENCIAL);
		}

		evento.setStatus(StatusEvento.EM_ESPERA);

		this.eventoRepository.save(evento);

		return eventoMapper.toDTO(evento);
	}

	public EventoResponseDTO editarEvento(Long id, EventoRequestDTO eventoRequestDTO) {
		var evento = this.buscarEvento(id);

		String site = this.verificarSite(eventoRequestDTO.site());
		evento.setSite(site);

		this.eventoRepository.save(evento);

		return eventoMapper.toDTO(evento);
	}

	public EventoResponseDTO aprovarEvento(Long id) {
		var evento = this.buscarEvento(id);
		evento.setStatus(StatusEvento.APROVADO);

		this.eventoRepository.save(evento);

		return eventoMapper.toDTO(evento);
	}

	public void apagarEventoPorId(Long id) {
		this.buscarEvento(id);
		this.eventoRepository.deleteById(id);
	}

	public void apagarTodosEvento() {
		this.eventoRepository.deleteAll();
	}

	public Page<EventoResponseDTO> listarEventosAprovados(int page, int size, String order) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, order);
		return this.eventoRepository.listarEventosAprovados(pageRequest).map(eventoMapper::toDTO);
	}

	public Page<EventoResponseDTO> listarEventosEmEspera(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
		return this.eventoRepository.listarEventosEmEspera(pageRequest).map(eventoMapper::toDTO);
	}

	public EventoResponseDTO buscarEventoPeloId(Long id) {
		var evento = buscarEvento(id);
		return eventoMapper.toDTO(evento);
	}

	public Optional<Evento> buscarEventoPorId(Long id) {
		return this.eventoRepository.buscarEventoPorId(id);
	}

	public Page<EventoResponseDTO> buscarEventoPorNome(String nome, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
		return this.eventoRepository.buscarEventoPorNome(nome, pageRequest).map(eventoMapper::toDTO);
	}

	public Model setModel(Model model, Page<EventoResponseDTO> eventos) {
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

	private Evento buscarEvento(Long id) {
		return eventoRepository.findById(id).orElseThrow(() -> new EventoNotFoundException("Video não encontrado"));
	}
}
