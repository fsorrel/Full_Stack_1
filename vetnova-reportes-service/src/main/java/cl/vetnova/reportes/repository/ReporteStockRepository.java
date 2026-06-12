package cl.vetnova.reportes.repository;

import cl.vetnova.reportes.model.ReporteStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReporteStockRepository extends JpaRepository<ReporteStock, Long> {

    Optional<ReporteStock> findByReporteId(Long reporteId);
}
