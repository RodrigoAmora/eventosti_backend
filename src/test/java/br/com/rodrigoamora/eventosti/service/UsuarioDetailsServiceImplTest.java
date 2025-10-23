package br.com.rodrigoamora.eventosti.service;

import br.com.rodrigoamora.eventosti.entity.Usuario;
import br.com.rodrigoamora.eventosti.entity.role.Role;
import br.com.rodrigoamora.eventosti.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioDetailsServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioDetailsServiceImpl usuarioDetailsService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        // Arrange
        String email = "test@example.com";
        Role role = new Role();
        role.setName("ROLE_USER");

        Usuario mockUser = new Usuario();
        mockUser.setLogin(email);
        mockUser.setPassword("encodedPassword123");
        mockUser.setRoles(Arrays.asList(role));

        when(usuarioRepository.findByLogin(email)).thenReturn(mockUser);

        // Act
        UserDetails userDetails = usuarioDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("encodedPassword123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));

        verify(usuarioRepository, times(1)).findByLogin(email);
    }

    @Test
    void testLoadUserByUsernameUserNotFound() {
        // Arrange
        String email = "notfound@example.com";
        when(usuarioRepository.findByLogin(email)).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> usuarioDetailsService.loadUserByUsername(email));
        verify(usuarioRepository, times(1)).findByLogin(email);
    }


}
