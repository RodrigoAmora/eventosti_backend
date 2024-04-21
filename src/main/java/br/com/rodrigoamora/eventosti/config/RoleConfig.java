package br.com.rodrigoamora.eventosti.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.rodrigoamora.eventosti.entity.role.ERole;
import br.com.rodrigoamora.eventosti.entity.role.Role;
import br.com.rodrigoamora.eventosti.repository.RoleRepository;

@Configuration
public class RoleConfig {

	@Autowired
	RoleRepository roleRepository;
	
	@Bean
	Boolean createRolesIfDontExist() {
		if (this.roleRepository.findByName(ERole.ROLE_ADMIN.name()) == null) {
			Role roleAdmin = new Role();
			roleAdmin.setName(ERole.ROLE_ADMIN.name());
			
			this.roleRepository.save(roleAdmin);
		}
		
		if (this.roleRepository.findByName(ERole.ROLE_MODERATOR.name()) == null) {
			Role roleModerator = new Role();
			roleModerator.setName(ERole.ROLE_MODERATOR.name());
			
			this.roleRepository.save(roleModerator);
		}
		
		if (this.roleRepository.findByName(ERole.ROLE_USER.name()) == null) {
			Role roleUser = new Role();
			roleUser.setName(ERole.ROLE_USER.name());
			
			this.roleRepository.save(roleUser);
		}
		
		return true;
	}
	
}
