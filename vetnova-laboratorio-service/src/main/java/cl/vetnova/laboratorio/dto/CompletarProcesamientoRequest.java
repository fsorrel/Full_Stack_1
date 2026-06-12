package cl.vetnova.laboratorio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CompletarProcesamientoRequest(@NotBlank @Size(max = 700) String observaciones) {}
