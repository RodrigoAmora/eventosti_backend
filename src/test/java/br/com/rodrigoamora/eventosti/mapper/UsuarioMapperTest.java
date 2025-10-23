package br.com.rodrigoamora.eventosti.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import br.com.rodrigoamora.eventosti.dto.request.UsuarioRequestDTO;
import br.com.rodrigoamora.eventosti.dto.response.UsuarioResponseDTO;
import br.com.rodrigoamora.eventosti.entity.Usuario;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsuarioMapperTest {

    @InjectMocks
    private UsuarioMapper usuarioMapper;

    @Test
    void testToEntitySuccess() {
        // Arrange
        UsuarioRequestDTO dto = new UsuarioRequestDTO(
                "Rodrigo Amora",
                "rodrigo@amora.com",
                "securePassword123"
        );

        // Act
        Usuario usuario = usuarioMapper.toEntity(dto);

        // Assert
        assertNotNull(usuario);
        assertEquals("Rodrigo Amora", usuario.getNome());
        assertEquals("rodrigo@amora.com", usuario.getLogin());
        assertEquals("securePassword123", usuario.getPassword());
    }

    @Test
    void testToEntityNullDto() {
        // Act
        Usuario usuario = usuarioMapper.toEntity(null);

        // Assert
        assertNull(usuario, "Quando o DTO for nulo, o método deve retornar null.");
    }

    @Test
    void testToResponseDTOSuccess() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Rodrigo Amora");
        usuario.setLogin("rodrigo@amora.com");
        usuario.setHasError("");

        // Act
        UsuarioResponseDTO responseDTO = usuarioMapper.toResponseDTO(usuario);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.id());
        assertEquals("Rodrigo Amora", responseDTO.nome());
        assertEquals("rodrigo@amora.com", responseDTO.login());
        assertTrue(responseDTO.hasError().isEmpty());
    }

    @Test
    void testToResponseDTONullEntity() {
        // Act
        UsuarioResponseDTO responseDTO = usuarioMapper.toResponseDTO(null);

        // Assert
        assertNull(responseDTO, "Quando a entidade for nula, o método deve retornar null.");
    }
}
