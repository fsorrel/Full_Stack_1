package cl.vetnova.laboratorio.controller;

import cl.vetnova.laboratorio.dto.ApiResponse;
import cl.vetnova.laboratorio.dto.TipoExamenRequest;
import cl.vetnova.laboratorio.dto.TipoExamenResponse;
import cl.vetnova.laboratorio.service.TipoExamenService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tipos-examen")
public class TipoExamenController {
    private final TipoExamenService service;
    public TipoExamenController(TipoExamenService service) { this.service = service; }
    @GetMapping public ResponseEntity<ApiResponse<List<TipoExamenResponse>>> listar() { return ResponseEntity.ok(ApiResponse.ok("Tipos de examen encontrados", service.listar())); }
    @GetMapping("/{id}") public ResponseEntity<ApiResponse<TipoExamenResponse>> buscar(@PathVariable Long id) { return ResponseEntity.ok(ApiResponse.ok("Tipo de examen encontrado", service.buscar(id))); }
    @PostMapping public ResponseEntity<ApiResponse<TipoExamenResponse>> crear(@Valid @RequestBody TipoExamenRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Tipo de examen creado", service.crear(request))); }
    @PutMapping("/{id}") public ResponseEntity<ApiResponse<TipoExamenResponse>> actualizar(@PathVariable Long id, @Valid @RequestBody TipoExamenRequest request) { return ResponseEntity.ok(ApiResponse.ok("Tipo de examen actualizado", service.actualizar(id, request))); }
    @PatchMapping("/{id}/desactivar") public ResponseEntity<ApiResponse<Void>> desactivar(@PathVariable Long id) { service.desactivar(id); return ResponseEntity.ok(ApiResponse.ok("Tipo de examen desactivado", null)); }
}
