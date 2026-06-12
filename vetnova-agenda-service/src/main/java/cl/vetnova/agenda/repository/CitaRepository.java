package cl.vetnova.agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.vetnova.agenda.model.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

}
