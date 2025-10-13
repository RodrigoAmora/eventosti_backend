package br.com.rodrigoamora.eventosti.dto.response;

import br.com.rodrigoamora.eventosti.entity.TipoEvento;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record EventoResponseDTO (
		Long id,

		String nome,

		String descricao,

		String site,

		@JsonFormat(pattern = "dd-MM-yyyy")
		LocalDate dataInicio,

		@JsonFormat(pattern = "dd-MM-yyyy")
		LocalDate dataFim,

		TipoEvento tipoEvento,

		String hasError
) {}
