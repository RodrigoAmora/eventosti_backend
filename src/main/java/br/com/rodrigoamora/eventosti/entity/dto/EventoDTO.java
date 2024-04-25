package br.com.rodrigoamora.eventosti.entity.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.rodrigoamora.eventosti.entity.Evento;
import br.com.rodrigoamora.eventosti.entity.TipoEvento;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;

public class EventoDTO {

	@NotEmpty(message = "{message.evento.name_empty}")
	private String nome;
	
	private String descricao;
	
	@NotEmpty(message = "{message.evento.site_empty}")
	private String site;
	
	@Future(message = "{message.evento.date_start}")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataInicio;
	
	@Future(message = "{message.evento.date_end}")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataFim;

	@Enumerated(EnumType.STRING)
	private TipoEvento tipoEvento;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	
	public Evento converterParaEvento() {
		Evento evento = new Evento();
		evento.setDataFim(dataFim);
		evento.setDataInicio(dataInicio);
		evento.setDescricao(descricao);
		evento.setNome(nome);
		evento.setSite(site);
		evento.setTipoEvento(tipoEvento);
		
		return evento;
	}
	
}
