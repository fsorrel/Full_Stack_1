package cl.vetnova.laboratorio.dto;

import cl.vetnova.laboratorio.model.Muestra;
import java.time.LocalDateTime;

public record MuestraResponse(Long id, Long ordenExamenId, String tipo, String descripcion, String codigoMuestra, LocalDateTime fechaRecepcion, String estadoProcesamiento, String responsableRecepcion) {
    public static MuestraResponse from(Muestra m) { return new MuestraResponse(m.getId(), m.getOrdenExamen().getId(), m.getTipo(), m.getDescripcion(), m.getCodigoMuestra(), m.getFechaRecepcion(), m.getEstadoProcesamiento(), m.getResponsableRecepcion()); }
}
