package cl.vetnova.notificaciones.repository;

import cl.vetnova.notificaciones.model.HistorialMensaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialMensajeRepository extends JpaRepository<HistorialMensaje, Long> {
    List<HistorialMensaje> findByNotificacionId(Long notificacionId);
    List<HistorialMensaje> findByDestinatarioId(Long destinatarioId);
    List<HistorialMensaje> findByEstado(String estado);
}
