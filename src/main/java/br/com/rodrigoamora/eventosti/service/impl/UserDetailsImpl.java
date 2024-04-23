package br.com.rodrigoamora.eventosti.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.rodrigoamora.eventosti.entity.Usuario;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String id;

	private String login;

	private String name;

	
	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImpl(String id, String login, String name, String password,
			Collection<? extends GrantedAuthority> authorities) {		
		this.id = id;
		this.login = login;
		this.name = name;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static UserDetailsImpl build(Usuario user) {

		List<GrantedAuthority> authorities = user.getRoles().stream()
		        								.map(role -> new SimpleGrantedAuthority(role.getName()))
		        								.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId().toString(),
		        user.getEmail(),
		        user.getNome(),
		        user.getSenha(),
		        authorities);
	}
		  
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getId() {
	    return id;
	  }
	
	public String getName() {
	    return name;
	  }
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
