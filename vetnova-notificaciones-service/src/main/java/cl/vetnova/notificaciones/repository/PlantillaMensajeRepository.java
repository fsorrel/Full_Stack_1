package cl.vetnova.notificaciones.repository;

import cl.vetnova.notificaciones.model.PlantillaMensaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlantillaMensajeRepository extends JpaRepository<PlantillaMensaje, Long> {
    List<PlantillaMensaje> findByMotivo(String motivo);
    List<PlantillaMensaje> findByCanal(String canal);
    Optional<PlantillaMensaje> findByMotivoAndCanal(String motivo, String canal);
    List<PlantillaMensaje> findByActivaTrue();
}
