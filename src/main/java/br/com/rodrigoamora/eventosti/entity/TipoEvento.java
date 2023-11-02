package br.com.rodrigoamora.eventosti.entity;

public enum TipoEvento {

	PRESENCIAL("PRESENCIAL"),
	ON_LINE("ON-LINE"),
	HIBIRDO("HIBRIDO");
	
	private String tipoEvento;
	
	TipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	
}
