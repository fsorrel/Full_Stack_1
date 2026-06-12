package cl.vetnova.soporte.dto;

import cl.vetnova.soporte.model.RespuestaTicket;
import java.time.LocalDateTime;

public record RespuestaTicketResponse(Long id, Long ticketId, Long autorId, String contenido, LocalDateTime fecha, Boolean visible) {
    public static RespuestaTicketResponse from(RespuestaTicket r) { return new RespuestaTicketResponse(r.getId(), r.getTicket().getId(), r.getAutorId(), r.getContenido(), r.getFecha(), r.getVisible()); }
}
