package cl.vetnova.soporte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ResponderTicketRequest(
        @NotNull Long autorId,
        @NotBlank @Size(min = 3, max = 1200) String contenido,
        Boolean visible
) {}
