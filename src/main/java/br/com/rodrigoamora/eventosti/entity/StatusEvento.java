package br.com.rodrigoamora.eventosti.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusEvento {

	EM_ESPERA("EM_ESPERA"),
	APROVADO("APROVADO");
	
	private String status;
	
	StatusEvento(String status) {
		this.status = status;
	}

	@JsonValue
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
