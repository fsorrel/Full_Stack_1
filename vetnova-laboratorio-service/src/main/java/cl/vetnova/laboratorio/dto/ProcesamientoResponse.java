package cl.vetnova.laboratorio.dto;

import cl.vetnova.laboratorio.model.Procesamiento;
import java.time.LocalDateTime;

public record ProcesamientoResponse(Long id, Long muestraId, Long tecnicoId, String metodologia, LocalDateTime fechaInicio, LocalDateTime fechaFin, String estado, String observaciones) {
    public static ProcesamientoResponse from(Procesamiento p) { return new ProcesamientoResponse(p.getId(), p.getMuestra().getId(), p.getTecnicoId(), p.getMetodologia(), p.getFechaInicio(), p.getFechaFin(), p.getEstado(), p.getObservaciones()); }
}
