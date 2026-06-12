package cl.vetnova.soporte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DerivarTicketRequest(
        @NotNull Long responsableNuevo,
        @NotBlank @Size(max = 300) String motivo
) {}
