package cl.vetnova.laboratorio.repository;

import cl.vetnova.laboratorio.model.TipoExamen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoExamenRepository extends JpaRepository<TipoExamen, Long> { boolean existsByNombreIgnoreCase(String nombre); }
