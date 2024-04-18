package br.com.rodrigoamora.eventosti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rodrigoamora.eventosti.entity.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

}
