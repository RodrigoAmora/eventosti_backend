package br.com.rodrigoamora.eventosti.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
        .requestMatchers("/aa/**").permitAll()
        .anyRequest().fullyAuthenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .failureUrl("/login?error=true")
//        .successHandler(customAuthenticationSuccessHandler)
        .usernameParameter("user")
        .passwordParameter("password")
        .permitAll()
        .and()
        .logout()
        .logoutUrl("/logout")
//        .logoutSuccessHandler(customLogoutSuccessHandler)
        .permitAll();
        return http.build();
    }
	
		
	
}
