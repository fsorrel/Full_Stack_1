package cl.vetnova.facturacion.repository;

import cl.vetnova.facturacion.model.ReporteTributario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReporteTributarioRepository extends JpaRepository<ReporteTributario, Long> {

    List<ReporteTributario> findBySucursal(String sucursal);
    Optional<ReporteTributario> findBySucursalAndPeriodo(String sucursal, String periodo);
    List<ReporteTributario> findByPeriodo(String periodo);
}
