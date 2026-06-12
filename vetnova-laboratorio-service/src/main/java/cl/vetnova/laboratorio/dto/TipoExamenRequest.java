package cl.vetnova.laboratorio.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TipoExamenRequest(
        @NotBlank @Size(max = 100) String nombre,
        @NotBlank @Size(max = 500) String descripcion,
        @NotNull @Min(1) @Max(240) Integer tiempoEstimadoHoras,
        @NotNull Boolean requiereMuestra,
        @NotBlank @Size(max = 700) String instrucciones
) {}
