package br.com.rodrigoamora.eventosti.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoEvento {

	PRESENCIAL,
	ON_LINE,
	HIBIRDO;
	
	private String tipoEvento;	

	@JsonValue
	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	
}
