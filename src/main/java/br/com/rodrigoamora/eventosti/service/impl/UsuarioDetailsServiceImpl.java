package br.com.rodrigoamora.eventosti.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.rodrigoamora.eventosti.entity.Usuario;
import br.com.rodrigoamora.eventosti.entity.role.Role;
import br.com.rodrigoamora.eventosti.repository.UsuarioRepository;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;

	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario user = this.repository.findByLogin(email);
//		UserDetailsImpl user = UserDetailsImpl.build(userLogado);
        if (user != null) {
//            return new User(user.getEmail(),
//                    user.getSenha(),
//                    mapRolesToAuthorities(user.getRoles()));
        	return UserDetailsImpl.build(user);
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

	private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
        Collection < ? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }
    
}
