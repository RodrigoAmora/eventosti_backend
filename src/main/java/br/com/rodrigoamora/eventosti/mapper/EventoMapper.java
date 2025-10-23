package br.com.rodrigoamora.eventosti.mapper;

import br.com.rodrigoamora.eventosti.dto.request.EventoRequestDTO;
import br.com.rodrigoamora.eventosti.dto.response.EventoResponseDTO;
import br.com.rodrigoamora.eventosti.entity.Evento;
import org.springframework.stereotype.Component;

@Component
public class EventoMapper {

    private EventoMapper() {}

    public Evento toEntity(EventoRequestDTO dto) {
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

    public EventoResponseDTO toDTO(Evento evento) {
        if (evento == null) {
            return null;
        }

        return new EventoResponseDTO(evento.getId(), evento.getNome(), evento.getDescricao(), evento.getSite(),
                                                    evento.getDataInicio(), evento.getDataFim(), evento.getTipoEvento());
    }
}
