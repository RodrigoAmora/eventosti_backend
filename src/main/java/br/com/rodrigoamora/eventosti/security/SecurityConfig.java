package br.com.rodrigoamora.eventosti.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import br.com.rodrigoamora.eventosti.security.filter.CustomHeaderFilter;
import br.com.rodrigoamora.eventosti.security.jwt.JWTLoginFilter;
import br.com.rodrigoamora.eventosti.security.jwt.TokenAuthenticationService;
import br.com.rodrigoamora.eventosti.service.BlackListAccessTokenService;
import br.com.rodrigoamora.eventosti.service.impl.UsuarioDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
		prePostEnabled = true, 
		securedEnabled = true, 
		jsr250Enabled = true
)
public class SecurityConfig {

	@Autowired
	private BlackListAccessTokenService blackListTokenService;
	
	@Autowired
	private UsuarioDetailsServiceImpl userDetailsService;
	
	@Autowired
	private AuthenticationConfiguration authConfig;
	
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(req -> {
				req.requestMatchers(HttpMethod.GET, "/").permitAll();

				req.requestMatchers("/download").permitAll();
				req.requestMatchers("/privacy").permitAll();
				req.requestMatchers("/redoc").permitAll();
				req.requestMatchers("/swagger").permitAll();

				// Endpoint de monitoramento
				req.requestMatchers("/actuator/**").permitAll();
				
				// Eventps
				req.requestMatchers("/evento/cadastrar").permitAll();
				req.requestMatchers("/evento/buscar").permitAll();
				req.requestMatchers("/verEvento").permitAll();
				
				// Login
				req.requestMatchers(HttpMethod.POST, "/login").permitAll();
				
				// API
				req.requestMatchers(HttpMethod.GET, "/api/evento/**").permitAll();
				
				
				req.anyRequest().authenticated();
			})
			// filtra requisições de login
	        .addFilterBefore(new JWTLoginFilter("/login", this.authenticationManager(), this.tokenAuthorizationFilter()),
	                UsernamePasswordAuthenticationFilter.class)
	        // filtra outras requisições para verificar a presença do JWT no header
			.authenticationProvider(this.authenticationProvider())
			.addFilterBefore(this.customHeaderFilter(), UsernamePasswordAuthenticationFilter.class)
			.formLogin(
                form -> form
                .loginPage("/formLogin")
                .loginProcessingUrl("/formLogin")
                .defaultSuccessUrl("/")
                .permitAll()
            )
			.logout(
                logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
			);

        return http.build();
    }
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/ignore2", "/swagger-ui/**", "/swagger-ui.html",
																	"/v3/api-docs/**", "/v2/api-docs/**", "/redoc.html",
																	"css/**", "js/**", "assets/**");
	}

	@Bean
	AuthenticationManager authenticationManager() throws Exception {
		return this.authConfig.getAuthenticationManager();
	}
	
	@Bean
  	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();		
		authProvider.setUserDetailsService(this.userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	static PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	SpringSecurityDialect securityDialect() {
	    return new SpringSecurityDialect();
	}
	
	@Bean
	CustomHeaderFilter customHeaderFilter() {
		return new CustomHeaderFilter(this.tokenAuthorizationFilter());
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

	private TokenAuthenticationService tokenAuthorizationFilter() {
        return new TokenAuthenticationService(this.userDetailsService, this.blackListTokenService);
    }
    
}
