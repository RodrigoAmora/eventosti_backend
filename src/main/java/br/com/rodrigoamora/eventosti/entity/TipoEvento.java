package br.com.rodrigoamora.eventosti.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoEvento {

	PRESENCIAL("PRESENCIAL"),
	ON_LINE("ON_LINE"),
	HIBIRDO("HIBIRDO");
	
	private String tipoEvento;	

	TipoEvento(String tipo) {
		this.tipoEvento = tipo;
	}
	
	@JsonValue
	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	
}
