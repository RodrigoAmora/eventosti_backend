package br.com.rodrigoamora.eventosti.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.rodrigoamora.eventosti.entity.Usuario;
import br.com.rodrigoamora.eventosti.repository.UsuarioRepository;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;

	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario userLogado = this.repository.findByEmail(email);
		UserDetailsImpl user = UserDetailsImpl.build(userLogado);
        if (user != null) {
            return new User(user.getUsername(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getAuthorities()));
        }else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(Collection<? extends GrantedAuthority> collection) {
        Collection < ? extends GrantedAuthority> mapRoles = collection.stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
        return mapRoles;
    }
	
}
