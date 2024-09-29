package br.com.rodrigoamora.eventosti.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.rodrigoamora.eventosti.entity.Evento;

@ActiveProfiles("test")
@SpringBootTest
public class EventoServiceImplTest {

	@Autowired
	EventoServiceImpl eventoService;
	
	@Test
	public void salvarEventoComSucesso() {
		LocalDate hoje = LocalDate.now();
		
		Evento evento = new Evento();
		evento.setNome("Evento teste");
		evento.setDataInicio(hoje);
		evento.setDataFim(hoje.plusDays(1));
		evento.setSite("http://eventoteste.com.br");
		
		evento = this.eventoService.salvarEvento(evento);
		
		assertNotNull(evento.getId());
	}
	
	@Test
	public void salvarEventoSemSite() {
		LocalDate hoje = LocalDate.now();
		
		Evento evento = new Evento();
		evento.setNome("Evento teste");
		evento.setDataFim(hoje.plusDays(1));
		evento.setSite(null);
		
		evento = this.eventoService.salvarEvento(evento);
		
		assertNotNull(evento.getId());
		assertEquals("", evento.getSite());
	}
	
	@Test
	public void salvarEventoComSiteSemHTTP() {
		LocalDate hoje = LocalDate.now();
		
		Evento evento = new Evento();
		evento.setNome("Evento teste");
		evento.setDataInicio(hoje);
		evento.setDataFim(hoje.plusDays(1));
		evento.setSite("eventoteste.com.br");
		
		evento = this.eventoService.salvarEvento(evento);
		
		assertNotNull(evento.getId());
		assertEquals("http://eventoteste.com.br", evento.getSite());
		assertNotEquals("eventoteste.com.br", evento.getSite());
	}
	
}
