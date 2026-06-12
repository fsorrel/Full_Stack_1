package cl.vetnova.soporte.dto;

import cl.vetnova.soporte.model.CategoriaTicket;

public record CategoriaTicketResponse(Long id, String nombre, String descripcion, String areaPorDefecto, Integer prioridadDefault, Boolean activo) {
    public static CategoriaTicketResponse from(CategoriaTicket c) {
        return new CategoriaTicketResponse(c.getId(), c.getNombre(), c.getDescripcion(), c.getAreaPorDefecto(), c.getPrioridadDefault(), c.getActivo());
    }
}
