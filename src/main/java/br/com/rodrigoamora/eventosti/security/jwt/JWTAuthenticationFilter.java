package br.com.rodrigoamora.eventosti.security.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends GenericFilterBean {

	private TokenAuthenticationService tokenAuthenticationService;
	
	public JWTAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
		this.tokenAuthenticationService = tokenAuthenticationService;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		Authentication authentication = tokenAuthenticationService
				.getAuthentication((HttpServletRequest) request, (HttpServletResponse) response);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

}
