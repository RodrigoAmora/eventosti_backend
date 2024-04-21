package br.com.rodrigoamora.eventosti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodrigoamora.eventosti.entity.role.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
