package cl.vetnova.soporte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EscalarTicketRequest(
        @NotNull Long administradorId,
        @NotBlank @Size(max = 400) String motivo
) {}
