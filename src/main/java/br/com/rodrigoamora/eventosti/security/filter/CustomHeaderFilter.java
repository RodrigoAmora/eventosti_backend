package br.com.rodrigoamora.eventosti.security.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.rodrigoamora.eventosti.security.jwt.TokenAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomHeaderFilter extends OncePerRequestFilter {

	private TokenAuthenticationService tokenAuthenticationService;
	
	public CustomHeaderFilter(TokenAuthenticationService tokenAuthenticationService) {
		this.tokenAuthenticationService = tokenAuthenticationService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		if (!requestURI.startsWith("/api")) {
			response.setHeader("Authorization", null);
		} else {
			Authentication authentication = this.tokenAuthenticationService
												.getAuthentication(request, response);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}

}
