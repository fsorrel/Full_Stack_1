package cl.vetnova.facturacion.repository;

import cl.vetnova.facturacion.model.EnvioSII;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnvioSIIRepository extends JpaRepository<EnvioSII, Long> {

    Optional<EnvioSII> findByDocumentoId(Long documentoId);
    List<EnvioSII> findByEstado(String estado);
    List<EnvioSII> findByReintentadoTrue();
}
