package br.com.rodrigoamora.eventosti.security.token;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reset_password_token")
public class ResetPasswordToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	private String token;
	
	private String userId;

	private Boolean bolcked;
	
	private LocalDateTime validity;
	
	public ResetPasswordToken() {
		this.generateValidity();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getBolcked() {
		return bolcked;
	}

	public void setBolcked(Boolean bolcked) {
		this.bolcked = bolcked;
	}

	public LocalDateTime getValidity() {
		return validity;
	}

	public void setValidity(LocalDateTime validity) {
		this.validity = validity;
	}
	
	public void generateValidity() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime validity = now.plusHours(2);
		this.setValidity(validity);
	}
	
}
