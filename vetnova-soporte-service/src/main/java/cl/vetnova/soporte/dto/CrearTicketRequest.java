package cl.vetnova.soporte.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CrearTicketRequest(
        @NotNull Long clienteId,
        @NotBlank @Size(max = 120) String motivo,
        @NotBlank @Size(min = 10, max = 1200) String descripcion,
        @NotNull Long categoriaId,
        @NotBlank @Pattern(regexp = "BAJA|MEDIA|ALTA|CRITICA") String prioridad,
        @NotBlank @Size(max = 60) String sucursal
) {}
