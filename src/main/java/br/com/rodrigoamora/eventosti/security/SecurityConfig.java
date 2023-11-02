package br.com.rodrigoamora.eventosti.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        jsr250Enabled = true
)
public class SecurityConfig {

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		
		http.csrf(csrf -> csrf.disable())

        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(req -> {
        	req.requestMatchers(HttpMethod.GET, "/api/listAllUsers").anonymous();
            req.requestMatchers(HttpMethod.POST, "/login").permitAll();
            req.anyRequest().authenticated();
        });
        //.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        
        
        return http.build();
    }
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/ignore2");
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf((csrf) -> csrf
	            .ignoringRequestMatchers("/no-csrf")
	        );
	    return http.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
	        throws Exception {
	    return configuration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

}
