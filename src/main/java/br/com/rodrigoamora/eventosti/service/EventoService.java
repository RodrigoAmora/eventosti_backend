package br.com.rodrigoamora.eventosti.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.rodrigoamora.eventosti.entity.Evento;

public interface EventoService {
	
	public Evento salvarEvento(Evento evento);
	public Evento editarEvento(Evento evento);
	public Evento aprovarEvento(Long id);
	
	public void apagarEventoPorId(Long id);
	public void apagarTodosEvento();
	
	public Page<Evento> listarEventosAprovados(int page, int size, String order);
	public Page<Evento> listarEventosEmEspera(int page, int size);
	
	public Page<Evento> buscarEventoPorNome(String nome, int page, int size);
	public Optional<Evento> buscarEventoPorId(Long id);

}
