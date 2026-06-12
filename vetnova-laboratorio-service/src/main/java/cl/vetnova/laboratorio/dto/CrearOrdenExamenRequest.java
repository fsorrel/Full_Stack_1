package cl.vetnova.laboratorio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CrearOrdenExamenRequest(
        @NotNull Long mascotaId,
        @NotNull Long veterinarioId,
        @NotNull Long recepcionistaId,
        @NotNull Long tipoExamenId,
        @NotBlank @Size(min = 10, max = 700) String descripcion,
        @NotBlank @Size(max = 60) String sucursal
) {}
