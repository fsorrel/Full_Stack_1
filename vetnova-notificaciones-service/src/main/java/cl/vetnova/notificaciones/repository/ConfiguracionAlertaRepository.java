package cl.vetnova.notificaciones.repository;

import cl.vetnova.notificaciones.model.ConfiguracionAlerta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfiguracionAlertaRepository extends JpaRepository<ConfiguracionAlerta, Long> {
    List<ConfiguracionAlerta> findByTipo(String tipo);
    List<ConfiguracionAlerta> findBySucursal(String sucursal);
    List<ConfiguracionAlerta> findByActivaTrue();
}
