package br.com.rodrigoamora.eventosti.service;

import br.com.rodrigoamora.eventosti.dto.request.UsuarioRequestDTO;
import br.com.rodrigoamora.eventosti.dto.response.UsuarioResponseDTO;
import br.com.rodrigoamora.eventosti.entity.Usuario;
import br.com.rodrigoamora.eventosti.entity.role.ERole;
import br.com.rodrigoamora.eventosti.entity.role.Role;
import br.com.rodrigoamora.eventosti.mapper.UsuarioMapper;
import br.com.rodrigoamora.eventosti.repository.RoleRepository;
import br.com.rodrigoamora.eventosti.repository.UsuarioRepository;
import br.com.rodrigoamora.eventosti.validador.SenhaValidador;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository userRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private SenhaValidador senhaValidador;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    public void testSalvarUsuario() {
        // Arrange
        UsuarioRequestDTO requestDTO = mock(UsuarioRequestDTO.class);
        when(requestDTO.login()).thenReturn("user.login");
        when(requestDTO.nome()).thenReturn("Test User");
        when(requestDTO.password()).thenReturn("password123");

        Role roleAdmin = mock(Role.class);
        when(roleRepository.findByName(ERole.ROLE_ADMIN.name())).thenReturn(roleAdmin);

        Usuario usuario = new Usuario();
        usuario.setLogin("user.login");
        usuario.setNome("Test User");
        when(userRepository.save(any(Usuario.class))).thenReturn(usuario);

        when(usuarioMapper.toEntity(requestDTO)).thenReturn(usuario);
        when(usuarioMapper.toResponseDTO(usuario)).thenCallRealMethod(); // Supondo que seja uma implementação funcional

        // Act
        UsuarioResponseDTO responseDTO = usuarioService.salvar(requestDTO);

        // Assert
        assertNotNull(responseDTO);
        assertEquals("user.login", usuario.getLogin());
        verify(userRepository, times(1)).save(any(Usuario.class));
        verify(roleRepository, times(1)).findByName(ERole.ROLE_ADMIN.name());
    }

    @Test
    public void testEditarUsuario() {
        // Arrange
        UsuarioRequestDTO requestDTO = mock(UsuarioRequestDTO.class);
        when(requestDTO.login()).thenReturn("user.edited");

        Usuario usuario = new Usuario();

        when(usuarioMapper.toEntity(requestDTO)).thenReturn(usuario);
        when(userRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.toResponseDTO(usuario)).thenReturn(mock(UsuarioResponseDTO.class));

        // Act
        UsuarioResponseDTO responseDTO = usuarioService.editar(requestDTO);

        // Assert
        assertNotNull(responseDTO);
        verify(userRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void testListarTodosUsuarios() {
        // Arrange
        int page = 0;
        int size = 5;
        Page<Usuario> pageResult = new PageImpl<>(Collections.emptyList());
        when(userRepository.listarTodos(any(Pageable.class))).thenReturn(pageResult);

        // Act
        Page<Usuario> result = usuarioService.listarTodos(page, size);

        // Assert
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        verify(userRepository, times(1)).listarTodos(any(Pageable.class));
    }

    @Test
    public void testApagarUsuario() {
        // Arrange
        Usuario usuario = new Usuario();

        // Act
        usuarioService.apagarUsuario(usuario);

        // Assert
        verify(userRepository, times(1)).delete(usuario);
    }

    @Test
    public void testApagarUsuarioPorId() {
        // Arrange
        Long id = 1L;

        // Act
        usuarioService.apagarUsuarioPorId(id);

        // Assert
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    public void testBuscarUsuarioPorId() {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario();
        when(userRepository.findById(id)).thenReturn(Optional.of(usuario));

        // Act
        Usuario result = usuarioService.buscarUsuarioPorId(id);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    public void testBuscarUsuarioPorLogin() {
        // Arrange
        String login = "user.login";
        Usuario usuario = new Usuario();
        when(userRepository.findByLogin(login)).thenReturn(usuario);

        // Act
        Usuario result = usuarioService.buscarUsuarioPorLogin(login);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findByLogin(login);
    }

    @Test
    public void testTrocarSenha() {
        // Arrange
        Usuario usuario = new Usuario();

        // Act
        usuarioService.trocarSenha(usuario);

        // Assert
        verify(userRepository, times(1)).save(usuario);
    }

    @Test
    public void testGetUsuarioLogado() {
        // Arrange
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user.login");

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        Usuario usuario = new Usuario();
        when(userRepository.findByLogin("user.login")).thenReturn(usuario);

        // Act
        Usuario result = usuarioService.getUsuarioLogado();

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findByLogin("user.login");
    }
}
