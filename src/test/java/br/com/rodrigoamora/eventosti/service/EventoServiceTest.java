package br.com.rodrigoamora.eventosti.service;

import br.com.rodrigoamora.eventosti.dto.request.EventoRequestDTO;
import br.com.rodrigoamora.eventosti.dto.response.EventoResponseDTO;
import br.com.rodrigoamora.eventosti.entity.Evento;
import br.com.rodrigoamora.eventosti.entity.StatusEvento;
import br.com.rodrigoamora.eventosti.entity.TipoEvento;
import br.com.rodrigoamora.eventosti.mapper.EventoMapper;
import br.com.rodrigoamora.eventosti.repository.EventoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventoServiceTest {

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private EventoMapper eventoMapper;

    @InjectMocks
    private EventoService eventoService;

    @Test
    public void testSalvarEvento() {
        // Arrange
        EventoRequestDTO requestDTO = mock(EventoRequestDTO.class);
        when(requestDTO.site()).thenReturn("site.com");

        Evento evento = new Evento();
        evento.setDescricao("Bla, bla, bla");
        evento.setTipoEvento(TipoEvento.PRESENCIAL);
        evento.setSite("evento.com.br");

        EventoResponseDTO responseDTO = mock(EventoResponseDTO.class);

        // Mocks para entidades e mapeamentos
        when(eventoMapper.toEntity(requestDTO)).thenReturn(evento);
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);
        when(eventoMapper.toDTO(evento)).thenReturn(responseDTO);

        // Act
        EventoResponseDTO result = eventoService.salvarEvento(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(StatusEvento.EM_ESPERA, evento.getStatus());
        assertEquals("Bla, bla, bla", evento.getDescricao());
        assertEquals(TipoEvento.PRESENCIAL, evento.getTipoEvento());
        assertEquals("http://site.com", evento.getSite());
        verify(eventoRepository, times(1)).save(evento);
    }

    @Test
    public void testSalvarEventoComSiteNull() {
        // Arrange
        EventoRequestDTO requestDTO = mock(EventoRequestDTO.class);
        when(requestDTO.site()).thenReturn(null);

        Evento evento = new Evento();
        evento.setDescricao("Bla, bla, bla");
        evento.setTipoEvento(TipoEvento.PRESENCIAL);
        evento.setSite("");

        EventoResponseDTO responseDTO = mock(EventoResponseDTO.class);

        // Mocks para entidades e mapeamentos
        when(eventoMapper.toEntity(requestDTO)).thenReturn(evento);
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);
        when(eventoMapper.toDTO(evento)).thenReturn(responseDTO);

        // Act
        EventoResponseDTO result = eventoService.salvarEvento(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(StatusEvento.EM_ESPERA, evento.getStatus());
        assertEquals("Bla, bla, bla", evento.getDescricao());
        assertEquals(TipoEvento.PRESENCIAL, evento.getTipoEvento());
        assertTrue(evento.getSite().isEmpty());
        verify(eventoRepository, times(1)).save(evento);
    }

    @Test
    public void testSalvarEventoSemTipo() {
        // Arrange
        EventoRequestDTO requestDTO = mock(EventoRequestDTO.class);
        when(requestDTO.site()).thenReturn("site.com");

        Evento evento = new Evento();
        evento.setDescricao("Bla, bla, bla");
        evento.setSite("evento.com.br");

        EventoResponseDTO responseDTO = mock(EventoResponseDTO.class);

        // Mocks para entidades e mapeamentos
        when(eventoMapper.toEntity(requestDTO)).thenReturn(evento);
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);
        when(eventoMapper.toDTO(evento)).thenReturn(responseDTO);

        // Act
        EventoResponseDTO result = eventoService.salvarEvento(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(StatusEvento.EM_ESPERA, evento.getStatus());
        assertEquals("Bla, bla, bla", evento.getDescricao());
        assertEquals(TipoEvento.PRESENCIAL, evento.getTipoEvento());
        assertEquals("http://site.com", evento.getSite());
        verify(eventoRepository, times(1)).save(evento);
    }

    @Test
    public void testSalvarEventoSemSite() {
        // Arrange
        EventoRequestDTO requestDTO = new EventoRequestDTO("Teste", null, "evento.com.br",
                LocalDate.now(), LocalDate.now().plusDays(1), null);

        Evento evento = new Evento();
        evento.setDescricao("Bla, bla, bla");
        evento.setTipoEvento(TipoEvento.PRESENCIAL);
        evento.setSite(null);

        EventoResponseDTO responseDTO = mock(EventoResponseDTO.class);

        // Mocks para entidades e mapeamentos
        when(eventoMapper.toEntity(requestDTO)).thenReturn(evento);
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);
        when(eventoMapper.toDTO(evento)).thenReturn(responseDTO);

        // Act
        EventoResponseDTO result = eventoService.salvarEvento(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(StatusEvento.EM_ESPERA, evento.getStatus());
        assertEquals("Bla, bla, bla", evento.getDescricao());
        assertEquals(TipoEvento.PRESENCIAL, evento.getTipoEvento());
        assertEquals("http://evento.com.br", evento.getSite());
        verify(eventoRepository, times(1)).save(evento);
    }

    @Test
    public void testSalvarEventoComDescricaoNula() {
        // Arrange
        EventoRequestDTO requestDTO = new EventoRequestDTO("Teste", null, "evento.com.br",
                LocalDate.now(), LocalDate.now().plusDays(1), TipoEvento.PRESENCIAL);

        Evento evento = new Evento();
        evento.setDescricao(null);
        evento.setTipoEvento(TipoEvento.PRESENCIAL);
        evento.setSite(null);

        EventoResponseDTO responseDTO = mock(EventoResponseDTO.class);

        // Mocks para entidades e mapeamentos
        when(eventoMapper.toEntity(requestDTO)).thenReturn(evento);
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);
        when(eventoMapper.toDTO(evento)).thenReturn(responseDTO);

        // Act
        EventoResponseDTO result = eventoService.salvarEvento(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(StatusEvento.EM_ESPERA, evento.getStatus());
        assertEquals("", evento.getDescricao());
        assertEquals(TipoEvento.PRESENCIAL, evento.getTipoEvento());
        assertEquals("http://evento.com.br", evento.getSite());
        verify(eventoRepository, times(1)).save(evento);
    }

    @Test
    public void testEditarEvento() {
        // Arrange
        Long eventoId = 1L;
        EventoRequestDTO requestDTO = mock(EventoRequestDTO.class);
        when(requestDTO.site()).thenReturn("site.com");

        Evento evento = new Evento();
        when(eventoRepository.findById(eventoId)).thenReturn(Optional.of(evento));

        EventoResponseDTO responseDTO = mock(EventoResponseDTO.class);
        when(eventoMapper.toDTO(evento)).thenReturn(responseDTO);

        // Act
        EventoResponseDTO result = eventoService.editarEvento(eventoId, requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("http://site.com", evento.getSite());
        verify(eventoRepository, times(1)).save(evento);
    }

    @Test
    public void testAprovarEvento() {
        // Arrange
        Long eventoId = 1L;
        Evento evento = new Evento();
        when(eventoRepository.findById(eventoId)).thenReturn(Optional.of(evento));

        EventoResponseDTO responseDTO = mock(EventoResponseDTO.class);
        when(eventoMapper.toDTO(evento)).thenReturn(responseDTO);

        // Act
        EventoResponseDTO result = eventoService.aprovarEvento(eventoId);

        // Assert
        assertNotNull(result);
        assertEquals(StatusEvento.APROVADO, evento.getStatus());
        verify(eventoRepository, times(1)).save(evento);
    }

    @Test
    public void testApagarEventoPorId() {
        // Arrange
        Long eventoId = 1L;
        Evento evento = mock(Evento.class);
        when(eventoRepository.findById(eventoId)).thenReturn(Optional.of(evento));

        // Act
        eventoService.apagarEventoPorId(eventoId);

        // Assert
        verify(eventoRepository, times(1)).deleteById(eventoId);
    }

    @Test
    public void testApagarTodosEventos() {
        // Act
        eventoService.apagarTodosEvento();

        // Assert
        verify(eventoRepository, times(1)).deleteAll();
    }

    @Test
    public void testListarEventosAprovados() {
        // Arrange
        int page = 0, size = 10;
        String order = "name";
        Page<Evento> eventosPage = new PageImpl<>(Collections.emptyList());

        when(eventoRepository.listarEventosAprovados(any(PageRequest.class))).thenReturn(eventosPage);

        // Act
        Page<EventoResponseDTO> result = eventoService.listarEventosAprovados(page, size, order);

        // Assert
        assertNotNull(result);
        verify(eventoRepository, times(1)).listarEventosAprovados(any(PageRequest.class));
    }

    @Test
    public void testSetModel_WithMultiplePages() {
        // Arrange
        Model model = mock(Model.class);
        List<EventoResponseDTO> eventosList = Arrays.asList(
            mock(EventoResponseDTO.class),
            mock(EventoResponseDTO.class)
        );
        Page<EventoResponseDTO> eventosPage = new PageImpl<>(
            eventosList,
            PageRequest.of(0, 2),
            5 // Total elements (3 pages: 2, 2, 1)
        );

        // Act
        Model result = eventoService.setModel(model, eventosPage);

        // Assert
        assertSame(model, result);
        
        // Verify page numbers (1, 2, 3)
        List<Integer> expectedPageNumbers = IntStream.rangeClosed(1, 3)
            .boxed()
            .collect(Collectors.toList());
        verify(model).addAttribute("pageNumbers", expectedPageNumbers);
        verify(model).addAttribute("eventos", eventosPage);
    }

    @Test
    public void testSetModel_WithSinglePage() {
        // Arrange
        Model model = mock(Model.class);
        List<EventoResponseDTO> eventosList = Collections.singletonList(
            mock(EventoResponseDTO.class)
        );
        Page<EventoResponseDTO> eventosPage = new PageImpl<>(
            eventosList,
            PageRequest.of(0, 10),
            1 // Total elements (1 page)
        );

        // Act
        Model result = eventoService.setModel(model, eventosPage);

        // Assert
        assertSame(model, result);
        
        // Verify page numbers (only 1 page)
        List<Integer> expectedPageNumbers = List.of(1);
        verify(model).addAttribute("pageNumbers", expectedPageNumbers);
        verify(model).addAttribute("eventos", eventosPage);
    }

    @Test
    public void testSetModel_WithEmptyPage() {
        // Arrange
        Model model = mock(Model.class);
        Page<EventoResponseDTO> eventosPage = Page.empty();

        // Act
        Model result = eventoService.setModel(model, eventosPage);

        // Assert
        assertSame(model, result);
        
        // Verify no page numbers are added when there are no pages
        verify(model, never()).addAttribute(eq("pageNumbers"), anyInt());
        verify(model).addAttribute("eventos", eventosPage);
    }

    @Test
    public void testListarEventosEmEspera() {
        // Arrange
        int page = 0, size = 10;
        Page<Evento> eventosPage = new PageImpl<>(Collections.emptyList());

        when(eventoRepository.listarEventosEmEspera(any(PageRequest.class))).thenReturn(eventosPage);

        // Act
        Page<EventoResponseDTO> result = eventoService.listarEventosEmEspera(page, size);

        // Assert
        assertNotNull(result);
        verify(eventoRepository, times(1)).listarEventosEmEspera(any(PageRequest.class));
    }

    @Test
    public void testBuscarEventoPeloId() {
        // Arrange
        Long eventoId = 1L;
        Evento evento = mock(Evento.class);

        when(eventoRepository.findById(eventoId)).thenReturn(Optional.of(evento));

        EventoResponseDTO responseDTO = mock(EventoResponseDTO.class);
        when(eventoMapper.toDTO(evento)).thenReturn(responseDTO);

        // Act
        EventoResponseDTO result = eventoService.buscarEventoPeloId(eventoId);

        // Assert
        assertNotNull(result);
        verify(eventoRepository, times(1)).findById(eventoId);
    }

    @Test
    public void testBuscarEventoPorId() {
        // Arrange
        Long eventoId = 1L;
        Evento evento = mock(Evento.class);

        when(eventoRepository.buscarEventoPorId(eventoId)).thenReturn(Optional.of(evento));

        // Act
        Optional<Evento> result = eventoService.buscarEventoPorId(eventoId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(evento, result.get());
        verify(eventoRepository, times(1)).buscarEventoPorId(eventoId);
    }

    @Test
    public void testBuscarEventoPorNome() {
        // Arrange
        String nome = "Nome do Evento";
        int page = 0, size = 10;
        Page<Evento> eventosPage = new PageImpl<>(Collections.emptyList());

        when(eventoRepository.buscarEventoPorNome(eq(nome), any(PageRequest.class))).thenReturn(eventosPage);

        // Act
        Page<EventoResponseDTO> result = eventoService.buscarEventoPorNome(nome, page, size);

        // Assert
        assertNotNull(result);
        verify(eventoRepository, times(1)).buscarEventoPorNome(eq(nome), any(PageRequest.class));
    }

}
