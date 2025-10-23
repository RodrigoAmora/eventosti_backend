package br.com.rodrigoamora.eventosti.validador;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    private final EmailValidator emailValidator = new EmailValidator();

    @Test
    void testValidateValidEmail() {
        // Arrange
        String email = "usuario@email.com";

        // Act
        Boolean result = emailValidator.validate(email);

        // Assert
        assertTrue(result, "O e-mail válido deveria retornar true.");
    }

    @Test
    void testValidateValidEmailWithSubdomain() {
        // Arrange
        String email = "user@subdomain.email.com";

        // Act
        Boolean result = emailValidator.validate(email);

        // Assert
        assertTrue(result, "O e-mail com subdomínio válido deveria retornar true.");
    }

    @Test
    void testValidateInvalidEmailNoAtSymbol() {
        // Arrange
        String email = "usuarioemail.com";

        // Act
        Boolean result = emailValidator.validate(email);

        // Assert
        assertFalse(result, "E-mails sem o símbolo '@' deveriam retornar false.");
    }

    @Test
    void testValidateInvalidEmailNoDomain() {
        // Arrange
        String email = "usuario@.com";

        // Act
        Boolean result = emailValidator.validate(email);

        // Assert
        assertFalse(result, "E-mails sem domínio válido deveriam retornar false.");
    }

    @Test
    void testValidateInvalidEmailWithoutTLD() {
        // Arrange
        String email = "usuario@email";

        // Act
        Boolean result = emailValidator.validate(email);

        // Assert
        assertFalse(result, "E-mails sem TLD (ex: '.com') deveriam retornar false.");
    }

    @Test
    void testValidateInvalidEmailWithSpaces() {
        // Arrange
        String email = "user @email.com";

        // Act
        Boolean result = emailValidator.validate(email);

        // Assert
        assertFalse(result, "E-mails com espaços deveriam retornar false.");
    }

    @Test
    void testValidateInvalidTLDFormat() {
        // Arrange
        String email = "usuario@email.c";

        // Act
        Boolean result = emailValidator.validate(email);

        // Assert
        assertFalse(result, "E-mails com TLD inválido (menos que 2 caracteres) deveriam retornar false.");
    }

    @Test
    void testValidateEmptyEmail() {
        // Arrange
        String email = "";

        // Act
        Boolean result = emailValidator.validate(email);

        // Assert
        assertFalse(result, "E-mails vazios deveriam retornar false.");
    }

    @Test
    void testValidateNullEmail() {
        // Arrange
        String email = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> emailValidator.validate(email), "E-mails nulos deveriam lançar NullPointerException.");
    }
}
