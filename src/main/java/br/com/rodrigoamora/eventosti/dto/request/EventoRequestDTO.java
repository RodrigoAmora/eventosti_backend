package br.com.rodrigoamora.eventosti.dto.request;

import br.com.rodrigoamora.eventosti.entity.TipoEvento;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public record EventoRequestDTO(
        @NotEmpty(message = "{message.evento.name_empty}")
        String nome,

        String descricao,

        @NotEmpty(message = "{message.evento.site_empty}")
        String site,

        @Future(message = "{message.evento.date_start}")
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate dataInicio,

        @Future(message = "{message.evento.date_end}")
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate dataFim,

        @Enumerated(EnumType.STRING)
        TipoEvento tipoEvento
) {}
