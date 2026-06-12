package cl.vetnova.fichaclinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.vetnova.fichaclinica.model.Vacuna;

@Repository
public interface VacunaRepository extends JpaRepository<Vacuna, Long> {

}