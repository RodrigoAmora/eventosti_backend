package br.com.rodrigoamora.eventosti.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="eventos")
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	private String descricao;
	
	private String site;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataInicio;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataFim;

	@Enumerated(EnumType.STRING)
	private TipoEvento tipoEvento;

	@JsonIgnore
	private StatusEvento status;

}
