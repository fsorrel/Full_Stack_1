package cl.vetnova.laboratorio.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ProgramarOrdenRequest(@NotNull @Future LocalDateTime fechaProgramada) {}
