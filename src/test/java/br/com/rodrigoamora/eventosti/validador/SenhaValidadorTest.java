package br.com.rodrigoamora.eventosti.validador;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class SenhaValidadorTest {

    private final SenhaValidador senhaValidador = new SenhaValidador();

    @Test
    void testEncryptPasswordSuccess() {
        // Arrange
        String rawPassword = "MinhaSenhaSegura123";

        // Act
        String encryptedPassword = senhaValidador.encryptPassword(rawPassword);

        // Assert
        assertNotNull(encryptedPassword); // Verifica que o resultado não é nulo
        assertNotEquals(rawPassword, encryptedPassword); // A senha codificada deve ser diferente da original
        assertTrue(new BCryptPasswordEncoder().matches(rawPassword, encryptedPassword)); // Verifica que a senha original corresponde ao hash gerado
    }

    @Test
    void testEncryptPasswordEmptyString() {
        // Arrange
        String rawPassword = "";

        // Act
        String encryptedPassword = senhaValidador.encryptPassword(rawPassword);

        // Assert
        assertNotNull(encryptedPassword);
        assertNotEquals(rawPassword, encryptedPassword); // Mesmo uma string vazia deve ser codificada
        assertTrue(new BCryptPasswordEncoder().matches(rawPassword, encryptedPassword));
    }

}
