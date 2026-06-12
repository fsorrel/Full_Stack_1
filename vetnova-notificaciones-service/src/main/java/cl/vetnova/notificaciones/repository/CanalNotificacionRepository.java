package cl.vetnova.notificaciones.repository;

import cl.vetnova.notificaciones.model.CanalNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CanalNotificacionRepository extends JpaRepository<CanalNotificacion, Long> {
    List<CanalNotificacion> findByTipo(String tipo);
    List<CanalNotificacion> findByActivoTrue();
    List<CanalNotificacion> findBySucursal(String sucursal);
}
