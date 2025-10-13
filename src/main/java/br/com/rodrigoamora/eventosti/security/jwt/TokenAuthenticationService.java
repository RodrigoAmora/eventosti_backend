package br.com.rodrigoamora.eventosti.security.jwt;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import br.com.rodrigoamora.eventosti.service.BlackListAccessTokenService;
import br.com.rodrigoamora.eventosti.service.UsuarioDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenAuthenticationService {
	
	private BlackListAccessTokenService blackListTokenService;
	private UsuarioDetailsServiceImpl usuarioDetailsService;
	
	// EXPIRATION_TIME = 10 dias
	static final long EXPIRATION_TIME = 860_000_000;
	
	public TokenAuthenticationService(UsuarioDetailsServiceImpl usuarioDetailsService, BlackListAccessTokenService blackListTokenService) {
		this.usuarioDetailsService = usuarioDetailsService;
		this.blackListTokenService = blackListTokenService;
	}
	

	public void addAuthentication(HttpServletResponse response, String username) {
		response.addHeader(JWTParams.HEADER_STRING, this.createToken(response, username));
	}
	
	public String createToken(HttpServletResponse response, String username) {
		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, JWTParams.SECRET)
				.compact();
		
		String token = JWTParams.TOKEN_PREFIX + " " + JWT;
		return token;
	}
	
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader(JWTParams.HEADER_STRING);
		
		if (token != null) {
			String tokenSplit = token.split(JWTParams.TOKEN_PREFIX)[1];
			if (this.blackListTokenService.getTokenBlocked(tokenSplit) != null) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				throw new MalformedJwtException(token);
			}
			
			// faz parse do token
			String user = null;
			
			try {
				user = Jwts.parser().setSigningKey(JWTParams.SECRET).parseClaimsJws(token.replace(JWTParams.TOKEN_PREFIX, "")).getBody().getSubject();
				
			} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				throw e;
			}
			
			if (user != null) {
				Collection<? extends GrantedAuthority> authorities = usuarioDetailsService.loadUserByUsername(user).getAuthorities();
				return new UsernamePasswordAuthenticationToken(user, null, authorities);
			}
		}
		
		return null;
	}

}
