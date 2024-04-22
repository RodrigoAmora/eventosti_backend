package br.com.rodrigoamora.eventosti.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
		prePostEnabled = true, 
		securedEnabled = true, 
		jsr250Enabled = true
)
public class SecurityConfig {

	@Autowired
    private UserDetailsService userDetailsService;
	
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
			.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(req -> {
				req.requestMatchers(HttpMethod.GET, "/").permitAll();
				req.requestMatchers(HttpMethod.GET, "/login").permitAll();
				req.requestMatchers(HttpMethod.GET, "/api/evento/**").permitAll();
				req.anyRequest().authenticated();
			}).formLogin(
                    form -> form
                    .loginPage("/formLogin")
                    .loginProcessingUrl("/formLogin")
                    .defaultSuccessUrl("/")
                    .permitAll()
			).logout(
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
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
	        throws Exception {
	    return configuration.getAuthenticationManager();
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

}
