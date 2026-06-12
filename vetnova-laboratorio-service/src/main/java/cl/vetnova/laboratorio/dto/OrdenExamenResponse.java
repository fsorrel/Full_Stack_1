package cl.vetnova.laboratorio.dto;

import cl.vetnova.laboratorio.model.OrdenExamen;
import java.time.LocalDateTime;

public record OrdenExamenResponse(
        Long id, Long mascotaId, Long veterinarioId, Long recepcionistaId, TipoExamenResponse tipoExamen,
        String descripcion, String estado, String sucursal, LocalDateTime fechaSolicitud, LocalDateTime fechaProgramada,
        MuestraResponse muestra, ResultadoExamenResponse resultado
) {
    public static OrdenExamenResponse from(OrdenExamen o) {
        return new OrdenExamenResponse(o.getId(), o.getMascotaId(), o.getVeterinarioId(), o.getRecepcionistaId(), TipoExamenResponse.from(o.getTipoExamen()),
                o.getDescripcion(), o.getEstado(), o.getSucursal(), o.getFechaSolicitud(), o.getFechaProgramada(),
                o.getMuestra() == null ? null : MuestraResponse.from(o.getMuestra()), o.getResultado() == null ? null : ResultadoExamenResponse.from(o.getResultado()));
    }
}
