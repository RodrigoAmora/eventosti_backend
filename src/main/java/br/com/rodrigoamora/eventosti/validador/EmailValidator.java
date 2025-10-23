package br.com.rodrigoamora.eventosti.validador;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidator {
	public Boolean validate(String email) {
		String regex = "^(?:[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:com|com\\.[a-z]{2,3}))$";
		return Pattern.matches(regex, email);
	}
}
