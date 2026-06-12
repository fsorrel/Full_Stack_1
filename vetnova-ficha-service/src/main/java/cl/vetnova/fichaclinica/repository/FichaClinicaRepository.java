package cl.vetnova.fichaclinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.vetnova.fichaclinica.model.FichaClinica;

@Repository
public interface FichaClinicaRepository extends JpaRepository<FichaClinica, Long> {

}