package cl.vetnova.reportes.repository;

import cl.vetnova.reportes.model.ReporteAtencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReporteAtencionRepository extends JpaRepository<ReporteAtencion, Long> {

    Optional<ReporteAtencion> findByReporteId(Long reporteId);

    List<ReporteAtencion> findAllByReporteIdIn(List<Long> reporteIds);
}
