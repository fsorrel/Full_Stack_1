package cl.vetnova.soporte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ActualizarTicketRequest(
        @NotBlank @Size(max = 120) String motivo,
        @NotBlank @Size(min = 10, max = 1200) String descripcion,
        @NotBlank @Pattern(regexp = "BAJA|MEDIA|ALTA|CRITICA") String prioridad,
        @NotBlank @Pattern(regexp = "ABIERTO|DERIVADO|ESCALADO|EN_REVISION|CERRADO") String estado
) {}
