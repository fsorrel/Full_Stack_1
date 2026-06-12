package cl.vetnova.soporte.repository;

import cl.vetnova.soporte.model.EscalamientoTicket;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscalamientoTicketRepository extends JpaRepository<EscalamientoTicket, Long> {
    Optional<EscalamientoTicket> findByTicketId(Long ticketId);
}
