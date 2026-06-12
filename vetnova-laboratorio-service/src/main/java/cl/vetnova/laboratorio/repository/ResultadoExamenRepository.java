package cl.vetnova.laboratorio.repository;

import cl.vetnova.laboratorio.model.ResultadoExamen;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultadoExamenRepository extends JpaRepository<ResultadoExamen, Long> { Optional<ResultadoExamen> findByOrdenExamenId(Long ordenExamenId); }
