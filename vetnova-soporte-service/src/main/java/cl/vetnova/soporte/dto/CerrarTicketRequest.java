package cl.vetnova.soporte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CerrarTicketRequest(@NotBlank @Size(min = 5, max = 800) String resolucion) {}
