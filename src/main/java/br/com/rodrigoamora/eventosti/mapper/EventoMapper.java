package br.com.rodrigoamora.eventosti.mapper;

import br.com.rodrigoamora.eventosti.dto.request.EventoRequestDTO;
import br.com.rodrigoamora.eventosti.dto.response.EventoResponseDTO;
import br.com.rodrigoamora.eventosti.entity.Evento;

public class EventoMapper {

    private EventoMapper() {}

    public static Evento toEntity(EventoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Evento evento = new Evento();
        evento.setNome(dto.nome());
        evento.setDescricao(dto.descricao());
        evento.setSite(dto.site());
        evento.setDataInicio(dto.dataInicio());
        evento.setDataFim(dto.dataFim());
        evento.setTipoEvento(dto.tipoEvento());

        return evento;
    }

    public static EventoResponseDTO toDTO(Evento evento) {
        if (evento == null) {
            return null;
        }

        return new EventoResponseDTO(evento.getId(), evento.getNome(), evento.getDescricao(), evento.getSite(),
                                                    evento.getDataInicio(), evento.getDataFim(), evento.getTipoEvento());
    }
}
