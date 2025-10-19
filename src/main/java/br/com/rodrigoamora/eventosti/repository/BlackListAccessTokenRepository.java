package br.com.rodrigoamora.eventosti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodrigoamora.eventosti.security.token.BlackListAccessToken;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListAccessTokenRepository extends JpaRepository<BlackListAccessToken, String> {
	
	public BlackListAccessToken findByToken(String token);
	
}
