package cl.vetnova.reportes.repository;

import cl.vetnova.reportes.model.ReporteVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReporteVentaRepository extends JpaRepository<ReporteVenta, Long> {

    Optional<ReporteVenta> findByReporteId(Long reporteId);
}
