package cl.vetnova.laboratorio.controller;

import cl.vetnova.laboratorio.dto.*;
import cl.vetnova.laboratorio.service.OrdenExamenService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ordenes-examen")
public class OrdenExamenController {
    private final OrdenExamenService service;
    public OrdenExamenController(OrdenExamenService service) { this.service = service; }
    @GetMapping public ResponseEntity<ApiResponse<List<OrdenExamenResponse>>> listar(@RequestParam(required = false) String estado) { return ResponseEntity.ok(ApiResponse.ok("Órdenes encontradas", service.listar(estado))); }
    @GetMapping("/{id}") public ResponseEntity<ApiResponse<OrdenExamenResponse>> buscar(@PathVariable Long id) { return ResponseEntity.ok(ApiResponse.ok("Orden encontrada", service.buscar(id))); }
    @PostMapping public ResponseEntity<ApiResponse<OrdenExamenResponse>> crear(@Valid @RequestBody CrearOrdenExamenRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Orden de examen creada", service.crear(request))); }
    @PostMapping("/{id}/programar") public ResponseEntity<ApiResponse<OrdenExamenResponse>> programar(@PathVariable Long id, @Valid @RequestBody ProgramarOrdenRequest request) { return ResponseEntity.ok(ApiResponse.ok("Orden programada", service.programar(id, request))); }
    @PostMapping("/{id}/muestra") public ResponseEntity<ApiResponse<MuestraResponse>> registrarMuestra(@PathVariable Long id, @Valid @RequestBody RegistrarMuestraRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Muestra registrada", service.registrarMuestra(id, request))); }
    @PostMapping("/{id}/procesamiento/iniciar") public ResponseEntity<ApiResponse<ProcesamientoResponse>> iniciarProcesamiento(@PathVariable Long id, @Valid @RequestBody IniciarProcesamientoRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Procesamiento iniciado", service.iniciarProcesamiento(id, request))); }
    @PostMapping("/{id}/procesamiento/completar") public ResponseEntity<ApiResponse<ProcesamientoResponse>> completarProcesamiento(@PathVariable Long id, @Valid @RequestBody CompletarProcesamientoRequest request) { return ResponseEntity.ok(ApiResponse.ok("Procesamiento completado", service.completarProcesamiento(id, request))); }
    @PostMapping("/{id}/resultado") public ResponseEntity<ApiResponse<ResultadoExamenResponse>> registrarResultado(@PathVariable Long id, @Valid @RequestBody RegistrarResultadoRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Resultado registrado", service.registrarResultado(id, request))); }
    @PostMapping("/{id}/cancelar") public ResponseEntity<ApiResponse<OrdenExamenResponse>> cancelar(@PathVariable Long id, @RequestBody(required = false) Map<String, String> body) { String motivo = body == null ? "Sin motivo informado" : body.getOrDefault("motivo", "Sin motivo informado"); return ResponseEntity.ok(ApiResponse.ok("Orden cancelada", service.cancelar(id, motivo))); }
}
