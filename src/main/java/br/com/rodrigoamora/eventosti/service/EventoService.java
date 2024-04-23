package br.com.rodrigoamora.eventosti.service;

import org.springframework.data.domain.Page;

import br.com.rodrigoamora.eventosti.entity.Evento;

public interface EventoService {
	
	public Evento salvarEvento(Evento evento);
	public Evento aprovarEvento(Long id);
	
	public void apagarEventoPorId(Long id);
	
	public Page<Evento> listarEventosAprovados(int page, int size);
	public Page<Evento> buscarEventoPorNome(String nome);

}
