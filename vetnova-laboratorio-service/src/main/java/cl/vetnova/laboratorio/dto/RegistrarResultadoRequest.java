package cl.vetnova.laboratorio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistrarResultadoRequest(
        @NotNull Long tecnicoId,
        @NotBlank @Size(max = 2000) String resultado,
        @Size(max = 1000) String observaciones,
        @NotBlank @Size(max = 1000) String interpretacion,
        Boolean disponible
) {}
