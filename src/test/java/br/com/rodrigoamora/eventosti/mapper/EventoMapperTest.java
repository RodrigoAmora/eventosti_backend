package br.com.rodrigoamora.eventosti.mapper;

import br.com.rodrigoamora.eventosti.entity.TipoEvento;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import br.com.rodrigoamora.eventosti.dto.request.EventoRequestDTO;
import br.com.rodrigoamora.eventosti.dto.response.EventoResponseDTO;
import br.com.rodrigoamora.eventosti.entity.Evento;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class EventoMapperTest {

    @InjectMocks
    private EventoMapper eventoMapper;

    @Test
    void testToEntitySuccess() {
        // Arrange
        EventoRequestDTO dto = new EventoRequestDTO(
                "Evento Teste",
                "Descrição do Evento",
                "https://www.evento.com",
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 2),
                TipoEvento.PRESENCIAL
        );

        // Act
        Evento evento = eventoMapper.toEntity(dto);

        // Assert
        assertNotNull(evento);
        assertEquals("Evento Teste", evento.getNome());
        assertEquals("Descrição do Evento", evento.getDescricao());
        assertEquals("https://www.evento.com", evento.getSite());
        assertEquals(LocalDate.of(2025, 1, 1), evento.getDataInicio());
        assertEquals(LocalDate.of(2025, 1, 2), evento.getDataFim());
        assertEquals(TipoEvento.PRESENCIAL, evento.getTipoEvento());
    }

    @Test
    void testToEntityNullDto() {
        // Act
        Evento evento = eventoMapper.toEntity(null);

        // Assert
        assertNull(evento, "Quando o DTO é nulo, o método deve retornar null.");
    }

    @Test
    void testToDTOSuccess() {
        // Arrange
        Evento evento = new Evento();
        evento.setId(1L);
        evento.setNome("Evento Teste");
        evento.setDescricao("Descrição do Evento");
        evento.setSite("https://www.evento.com");
        evento.setDataInicio(LocalDate.of(2025, 1, 1));
        evento.setDataFim(LocalDate.of(2025, 1, 2));
        evento.setTipoEvento(TipoEvento.ON_LINE);

        // Act
        EventoResponseDTO dto = eventoMapper.toDTO(evento);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals("Evento Teste", dto.nome());
        assertEquals("Descrição do Evento", dto.descricao());
        assertEquals("https://www.evento.com", dto.site());
        assertEquals(LocalDate.of(2025, 1, 1), dto.dataInicio());
        assertEquals(LocalDate.of(2025, 1, 2), dto.dataFim());
        assertEquals(TipoEvento.ON_LINE, dto.tipoEvento());
    }

    @Test
    void testToDTONullEntity() {
        // Act
        EventoResponseDTO dto = eventoMapper.toDTO(null);

        // Assert
        assertNull(dto, "Quando a entidade é nula, o método deve retornar null.");
    }
}
