package cl.vetnova.soporte.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaTicketRequest(
        @NotBlank @Size(max = 80) String nombre,
        @NotBlank @Size(max = 250) String descripcion,
        @NotBlank @Size(max = 60) String areaPorDefecto,
        @Min(1) @Max(5) Integer prioridadDefault
) {}
