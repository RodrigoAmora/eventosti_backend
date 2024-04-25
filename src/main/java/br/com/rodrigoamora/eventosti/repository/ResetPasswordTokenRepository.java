package br.com.rodrigoamora.eventosti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodrigoamora.eventosti.security.token.ResetPasswordToken;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, String> {

	public ResetPasswordToken findByToken(String token);
	
}
