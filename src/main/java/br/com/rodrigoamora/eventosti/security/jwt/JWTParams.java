package br.com.rodrigoamora.eventosti.security.jwt;

public class JWTParams {
	
	static final String SECRET = "MySecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	
	public static String getSecret() {
		return SECRET;
	}
	
	public static String getTokenPrefix() {
		return TOKEN_PREFIX;
	}
	
	public static String getHeaderString() {
		return HEADER_STRING;
	}
	
}
