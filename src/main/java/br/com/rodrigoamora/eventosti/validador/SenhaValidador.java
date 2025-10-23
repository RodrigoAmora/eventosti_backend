package br.com.rodrigoamora.eventosti.validador;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SenhaValidador {

	public String encryptPassword(String password) {
		String passwordEncrypted = new BCryptPasswordEncoder().encode(password);
		return passwordEncrypted;
	}
	
}
