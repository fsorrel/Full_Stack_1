package cl.vetnova.agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.vetnova.agenda.model.BloqueAgenda;

@Repository
public interface BloqueAgendaRepository extends JpaRepository<BloqueAgenda, Long> {

}