package cl.vetnova.laboratorio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IniciarProcesamientoRequest(
        @NotNull Long tecnicoId,
        @NotBlank @Size(max = 200) String metodologia,
        @Size(max = 700) String observaciones
) {}
