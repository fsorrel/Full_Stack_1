package cl.vetnova.laboratorio.repository;

import cl.vetnova.laboratorio.model.Procesamiento;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcesamientoRepository extends JpaRepository<Procesamiento, Long> { Optional<Procesamiento> findByMuestraId(Long muestraId); }
