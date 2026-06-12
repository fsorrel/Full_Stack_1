package cl.vetnova.facturacion.repository;

import cl.vetnova.facturacion.model.Folio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolioRepository extends JpaRepository<Folio, Long> {

    List<Folio> findBySucursal(String sucursal);
    List<Folio> findByTipoDocumento(String tipoDocumento);
    Optional<Folio> findBySucursalAndTipoDocumentoAndActivoTrue(String sucursal, String tipoDocumento);
    List<Folio> findByActivoTrue();
}
