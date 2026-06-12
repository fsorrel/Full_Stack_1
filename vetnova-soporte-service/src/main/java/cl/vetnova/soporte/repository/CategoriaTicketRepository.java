package cl.vetnova.soporte.repository;

import cl.vetnova.soporte.model.CategoriaTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaTicketRepository extends JpaRepository<CategoriaTicket, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
}
