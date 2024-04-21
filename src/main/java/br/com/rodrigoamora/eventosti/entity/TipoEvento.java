package br.com.rodrigoamora.eventosti.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoEvento {

	PRESENCIAL("PRESENCIAL"),
	ON_LINE("ON-LINE"),
	HIBIRDO("HIBRIDO");
	
	private String tipoEvento;
	
	TipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	@JsonValue
	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	
}
