package cl.vetnova.laboratorio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrarMuestraRequest(
        @NotBlank @Size(max = 80) String tipo,
        @NotBlank @Size(max = 500) String descripcion,
        @NotBlank @Size(max = 80) String codigoMuestra,
        @NotBlank @Size(max = 100) String responsableRecepcion
) {}
