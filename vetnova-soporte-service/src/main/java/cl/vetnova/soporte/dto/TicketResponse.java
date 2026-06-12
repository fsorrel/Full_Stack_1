package cl.vetnova.soporte.dto;

import cl.vetnova.soporte.model.Ticket;
import java.time.LocalDateTime;

public record TicketResponse(
        Long id,
        Long clienteId,
        String motivo,
        String descripcion,
        CategoriaTicketResponse categoria,
        String prioridad,
        String estado,
        Long responsableId,
        String sucursal,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaCierre,
        int totalRespuestas,
        int totalDerivaciones,
        boolean escalado
) {
    public static TicketResponse from(Ticket t) {
        return new TicketResponse(
                t.getId(), t.getClienteId(), t.getMotivo(), t.getDescripcion(), CategoriaTicketResponse.from(t.getCategoria()),
                t.getPrioridad(), t.getEstado(), t.getResponsableId(), t.getSucursal(), t.getFechaCreacion(), t.getFechaCierre(),
                t.getRespuestas().size(), t.getDerivaciones().size(), t.getEscalamiento() != null
        );
    }
}
