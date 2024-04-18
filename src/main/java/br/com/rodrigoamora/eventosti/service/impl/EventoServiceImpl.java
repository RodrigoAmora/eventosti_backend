package br.com.rodrigoamora.eventosti.service.impl;

import org.springframework.data.domain.Page;

import br.com.rodrigoamora.eventosti.entity.Evento;
import br.com.rodrigoamora.eventosti.service.EventoService;

public class EventoServiceImpl implements EventoService {

	@Override
	public Evento salvarEvento(Evento evento) {
		return null;
	}

	@Override
	public void apagarEventoPorId(Long id) {}

	@Override
	public Page<Evento> listarTodos() {
		return null;
	}

	@Override
	public Page<Evento> buscarEventoPorNome(String nome) {
		return null;
	}

}
