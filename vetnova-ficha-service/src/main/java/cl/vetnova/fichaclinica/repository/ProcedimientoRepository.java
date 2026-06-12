package cl.vetnova.fichaclinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.vetnova.fichaclinica.model.Procedimiento;

@Repository
public interface ProcedimientoRepository extends JpaRepository<Procedimiento, Long> {

}