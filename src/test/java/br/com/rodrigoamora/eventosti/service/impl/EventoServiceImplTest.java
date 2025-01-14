package br.com.rodrigoamora.eventosti.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.rodrigoamora.eventosti.entity.Evento;
import br.com.rodrigoamora.eventosti.entity.StatusEvento;
import br.com.rodrigoamora.eventosti.entity.TipoEvento;
import br.com.rodrigoamora.eventosti.repository.EventoRepository;


//@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class EventoServiceImplTest {

	@InjectMocks
	private EventoServiceImpl eventoService;
	
	@Mock
	private EventoRepository eventoRepository;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void salvarEvento() {
		LocalDate hoje = LocalDate.now();
		
		Evento eventoSalvo = new Evento();
		eventoSalvo.setNome("Evento teste");
		eventoSalvo.setDataInicio(hoje);
		eventoSalvo.setDataFim(hoje.plusDays(1));
		eventoSalvo.setSite("http://eventoteste.com.br");
		eventoSalvo.setTipoEvento(TipoEvento.HIBIRDO);
		
		when(this.eventoRepository.save(any(Evento.class))).thenReturn(eventoSalvo);
		
		Evento evento = this.eventoService.salvarEvento(eventoSalvo);
		
		assertEquals(StatusEvento.EM_ESPERA, evento.getStatus());
	}
	
	@Test
	public void salvarEventoSemSite() {
		LocalDate hoje = LocalDate.now();
		
		Evento eventoSalvo = new Evento();
		eventoSalvo.setNome("Evento teste");
		eventoSalvo.setDataFim(hoje.plusDays(1));
		eventoSalvo.setSite(null);
		
		when(this.eventoRepository.save(any(Evento.class))).thenReturn(eventoSalvo);
		
		Evento evento = this.eventoService.salvarEvento(eventoSalvo);
		
		assertEquals(StatusEvento.EM_ESPERA, evento.getStatus());
		assertEquals("", evento.getSite());
	}
	
	@Test
	public void salvarEventoComSiteSemHTTP() {
		LocalDate hoje = LocalDate.now();
		
		Evento eventoSalvo = new Evento();
		eventoSalvo.setNome("Evento teste");
		eventoSalvo.setDataInicio(hoje);
		eventoSalvo.setDataFim(hoje.plusDays(1));
		eventoSalvo.setSite("eventoteste.com.br");
		
		when(this.eventoRepository.save(any(Evento.class))).thenReturn(eventoSalvo);
		
		Evento evento = this.eventoService.salvarEvento(eventoSalvo);
		
		assertEquals(StatusEvento.EM_ESPERA, evento.getStatus());
		assertEquals("http://eventoteste.com.br", evento.getSite());
		assertNotEquals("eventoteste.com.br", evento.getSite());
	}
	
	@Test
	public void aprovarEvento() {
		LocalDate hoje = LocalDate.now();
		
		Evento eventoSalvo = new Evento();
		eventoSalvo.setId(1L);
		eventoSalvo.setNome("Evento teste");
		eventoSalvo.setDataInicio(hoje);
		eventoSalvo.setDataFim(hoje.plusDays(1));
		eventoSalvo.setSite("http://eventoteste.com.br");
		eventoSalvo.setTipoEvento(TipoEvento.HIBIRDO);
		
		when(this.eventoRepository.save(any(Evento.class))).thenReturn(eventoSalvo);
		Evento evento = this.eventoService.salvarEvento(eventoSalvo);
		
		when(this.eventoRepository.findById(1L)).thenReturn(Optional.of(evento));
		
		Evento eventoAprovado = this.eventoService.aprovarEvento(evento.getId());
		assertEquals(StatusEvento.APROVADO, eventoAprovado.getStatus());
	}
	
}
