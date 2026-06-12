package cl.vetnova.laboratorio.dto;

import cl.vetnova.laboratorio.model.TipoExamen;

public record TipoExamenResponse(Long id, String nombre, String descripcion, Integer tiempoEstimadoHoras, Boolean requiereMuestra, String instrucciones, Boolean activo) {
    public static TipoExamenResponse from(TipoExamen t) { return new TipoExamenResponse(t.getId(), t.getNombre(), t.getDescripcion(), t.getTiempoEstimadoHoras(), t.getRequiereMuestra(), t.getInstrucciones(), t.getActivo()); }
}
