package cl.vetnova.notificaciones.repository;

import cl.vetnova.notificaciones.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByDestinatarioId(Long destinatarioId);
    List<Notificacion> findByEstado(String estado);
    List<Notificacion> findByCanal(String canal);
}
