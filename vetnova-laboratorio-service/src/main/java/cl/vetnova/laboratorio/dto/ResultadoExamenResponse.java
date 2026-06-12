package cl.vetnova.laboratorio.dto;

import cl.vetnova.laboratorio.model.ResultadoExamen;
import java.time.LocalDateTime;

public record ResultadoExamenResponse(Long id, Long ordenExamenId, Long muestraId, Long tecnicoId, String resultado, String observaciones, String interpretacion, Boolean disponible, LocalDateTime fechaRegistro) {
    public static ResultadoExamenResponse from(ResultadoExamen r) { return new ResultadoExamenResponse(r.getId(), r.getOrdenExamen().getId(), r.getMuestra() == null ? null : r.getMuestra().getId(), r.getTecnicoId(), r.getResultado(), r.getObservaciones(), r.getInterpretacion(), r.getDisponible(), r.getFechaRegistro()); }
}
