package br.com.rodrigoamora.eventosti.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.rodrigoamora.eventosti.entity.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

	@Query("FROM Evento e WHERE e.dataInicio >= CURDATE() AND e.status=APROVADO")
	Page<Evento> listarEventosAprovados(Pageable pageable);
	
	@Query("FROM Evento e WHERE e.dataInicio >= CURDATE() AND e.status=EM_ESPERA")
	Page<Evento> listarEventosEmEspera(Pageable pageable);
	
	@Query("FROM Evento e WHERE e.nome like %:nome%")
	Page<Evento> buscarEventoPorNome(@Param("nome") String nome, Pageable pageable);
	
}
