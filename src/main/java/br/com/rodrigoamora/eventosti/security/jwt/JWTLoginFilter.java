package br.com.rodrigoamora.eventosti.security.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rodrigoamora.eventosti.entity.Usuario;
import br.com.rodrigoamora.eventosti.entity.role.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	private TokenAuthenticationService tokenAuthenticationService;
	
	public JWTLoginFilter(String url, AuthenticationManager authManager, TokenAuthenticationService tokenAuthenticationService) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
		this.tokenAuthenticationService = tokenAuthenticationService;
	}
	
	protected JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		Usuario credentials = new ObjectMapper()
								.readValue(request.getInputStream(), Usuario.class);
		
		List<SimpleGrantedAuthority> authorities = Arrays.asList();
		for (Role role : credentials.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.toString()));
		}
		
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
						credentials.getLogin(), 
						//new BCryptPasswordEncoder().encode(credentials.getPassword()),
						credentials.getPassword(),
						authorities)
				);
	}
	
	@Override
	protected void successfulAuthentication(
			HttpServletRequest request, 
			HttpServletResponse response,
			FilterChain filterChain,
			Authentication auth) throws IOException, ServletException {
		this.tokenAuthenticationService.addAuthentication(response, auth.getName());
	}
	
}
