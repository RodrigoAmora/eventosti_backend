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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

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
	
//	@Autowired
//    private UserDetailsService userDetailsService;
	
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(req -> {
				req.requestMatchers(HttpMethod.GET, "/").permitAll();
				
				// Endpoint de monitoramento
				req.requestMatchers("/actuator/**").permitAll();
				
				req.requestMatchers("/evento/cadastrar").permitAll();
				req.requestMatchers("/verEvento").permitAll();
				req.requestMatchers(HttpMethod.POST, "/login").permitAll();
				
				req.requestMatchers(HttpMethod.GET, "/api/evento/**").permitAll();
				
				req.anyRequest().authenticated();
			})
			.formLogin(
                form -> form
                .loginPage("/formLogin")
//                    .loginProcessingUrl("/formLogin")
                .defaultSuccessUrl("/")
                
//                    .usernameParameter("username")
//                    .passwordParameter("password")
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
		return (web) -> web.ignoring().requestMatchers("/ignore2", "/swagger-ui/**","/swagger-ui.html", "/v3/api-docs/**", "/v2/api-docs/**", "/redoc.html", "css/**", "js/**", "assets/**");
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
	SpringSecurityDialect securityDialect() {
	    return new SpringSecurityDialect();
	}
	
	@Bean
	static PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
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
