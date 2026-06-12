package cl.vetnova.laboratorio.repository;

import cl.vetnova.laboratorio.model.OrdenExamen;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenExamenRepository extends JpaRepository<OrdenExamen, Long> {
    List<OrdenExamen> findByMascotaId(Long mascotaId);
    List<OrdenExamen> findByEstadoIgnoreCase(String estado);
    List<OrdenExamen> findByVeterinarioId(Long veterinarioId);
}
