package br.com.rodrigoamora.eventosti.validador;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaValidador {

	public static String encryptPassword(String password) {
		String passwordEncrypted = new BCryptPasswordEncoder().encode(password);
		return passwordEncrypted;
	}
	
}
