package br.com.rodrigoamora.eventosti.service;

import br.com.rodrigoamora.eventosti.entity.Usuario;
import br.com.rodrigoamora.eventosti.entity.role.Role;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsImplTest {

    @Test
    void testUserDetailsImplConstructor() {
        // Arrange
        String id = "1";
        String login = "user@example.com";
        String name = "User Name";
        String password = "password123";
        Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        // Act
        UserDetailsImpl userDetails = new UserDetailsImpl(id, login, name, password, authorities);

        // Assert
        assertNotNull(userDetails);
        assertEquals(id, userDetails.getId());
        assertEquals(login, userDetails.getUsername());
        assertEquals(name, userDetails.getName());
        assertEquals(password, userDetails.getPassword());
        assertEquals(authorities, userDetails.getAuthorities());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testBuildFromUsuario() {
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");

        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");

        // Arrange
        Usuario mockUsuario = mock(Usuario.class);
        when(mockUsuario.getId()).thenReturn(1L);
        when(mockUsuario.getLogin()).thenReturn("user@example.com");
        when(mockUsuario.getNome()).thenReturn("User Name");
        when(mockUsuario.getPassword()).thenReturn("password123");
        when(mockUsuario.getRoles()).thenReturn(Arrays.asList(roleUser, roleAdmin));

        // Act
        UserDetailsImpl userDetails = UserDetailsImpl.build(mockUsuario);

        // Assert
        assertNotNull(userDetails);
        assertEquals("1", userDetails.getId());
        assertEquals("user@example.com", userDetails.getUsername());
        assertEquals("User Name", userDetails.getName());
        assertEquals("password123", userDetails.getPassword());
        assertEquals(2, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void testIsAccountNonExpired() {
        // Arrange
        UserDetailsImpl userDetails = new UserDetailsImpl(
                "1",
                "user@example.com",
                "User Name",
                "password123",
                List.of()
        );

        // Act & Assert
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        // Arrange
        UserDetailsImpl userDetails = new UserDetailsImpl(
                "1",
                "user@example.com",
                "User Name",
                "password123",
                List.of()
        );

        // Act & Assert
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        // Arrange
        UserDetailsImpl userDetails = new UserDetailsImpl(
                "1",
                "user@example.com",
                "User Name",
                "password123",
                List.of()
        );

        // Act & Assert
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        // Arrange
        UserDetailsImpl userDetails = new UserDetailsImpl(
                "1",
                "user@example.com",
                "User Name",
                "password123",
                List.of()
        );

        // Act & Assert
        assertTrue(userDetails.isEnabled());
    }
}
