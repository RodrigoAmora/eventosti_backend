package br.com.rodrigoamora.eventosti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rodrigoamora.eventosti.repository.BlackListAccessTokenRepository;
import br.com.rodrigoamora.eventosti.security.token.BlackListAccessToken;

@Component
public class BlackListAccessTokenService {

	@Autowired
	private BlackListAccessTokenRepository blackListAccessTokenRepository;
	
	public void blockToken(String token, String userId) {
		BlackListAccessToken blackListToken = new BlackListAccessToken();
		blackListToken.setToken(token);
		blackListToken.setUserId(userId);
		
		this.blackListAccessTokenRepository.save(blackListToken);
	}
	
	public BlackListAccessToken getTokenBlocked(String token) {
		return this.blackListAccessTokenRepository.findByToken(token);
	}
	
}
