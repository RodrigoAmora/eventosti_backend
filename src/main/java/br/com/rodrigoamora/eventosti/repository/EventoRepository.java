package br.com.rodrigoamora.eventosti.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.rodrigoamora.eventosti.entity.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

	@Query("FROM Evento e")
	Page<Evento> listarTodos(Pageable pageable);
	
}
