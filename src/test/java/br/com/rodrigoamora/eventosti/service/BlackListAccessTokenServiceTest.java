package br.com.rodrigoamora.eventosti.service;

import br.com.rodrigoamora.eventosti.repository.BlackListAccessTokenRepository;
import br.com.rodrigoamora.eventosti.security.token.BlackListAccessToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BlackListAccessTokenServiceTest {

    @Mock
    private BlackListAccessTokenRepository blackListAccessTokenRepository;

    @InjectMocks
    private BlackListAccessTokenService blackListAccessTokenService;

    @Test
    public void testBlockToken() {
        // Arrange
        String token = "sample_token";
        String userId = "user123";

        // Act
        blackListAccessTokenService.blockToken(token, userId);

        // Assert
        verify(blackListAccessTokenRepository, times(1)).save(argThat(blackListToken ->
                blackListToken.getToken().equals(token) && blackListToken.getUserId().equals(userId)));
    }

    @Test
    public void testGetTokenBlocked() {
        // Arrange
        String token = "sample_token";
        BlackListAccessToken expectedToken = new BlackListAccessToken();
        expectedToken.setToken(token);
        expectedToken.setUserId("user123");

        when(blackListAccessTokenRepository.findByToken(token)).thenReturn(expectedToken);

        // Act
        BlackListAccessToken result = blackListAccessTokenService.getTokenBlocked(token);

        // Assert
        assertNotNull(result);
        assertEquals(token, result.getToken());
        assertEquals("user123", result.getUserId());
        verify(blackListAccessTokenRepository, times(1)).findByToken(token);
    }

    @Test
    public void testGetTokenBlockedWhenNotFound() {
        // Arrange
        String token = "nonexistent_token";
        when(blackListAccessTokenRepository.findByToken(token)).thenReturn(null);

        // Act
        BlackListAccessToken result = blackListAccessTokenService.getTokenBlocked(token);

        // Assert
        assertNull(result);
        verify(blackListAccessTokenRepository, times(1)).findByToken(token);
    }

}
