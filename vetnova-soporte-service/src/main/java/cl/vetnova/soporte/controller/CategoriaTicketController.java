package cl.vetnova.soporte.controller;

import cl.vetnova.soporte.dto.ApiResponse;
import cl.vetnova.soporte.dto.CategoriaTicketRequest;
import cl.vetnova.soporte.dto.CategoriaTicketResponse;
import cl.vetnova.soporte.service.CategoriaTicketService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categorias-ticket")
public class CategoriaTicketController {
    private final CategoriaTicketService service;
    public CategoriaTicketController(CategoriaTicketService service) { this.service = service; }
    @GetMapping public ResponseEntity<ApiResponse<List<CategoriaTicketResponse>>> listar() { return ResponseEntity.ok(ApiResponse.ok("Categorías encontradas", service.listar())); }
    @GetMapping("/{id}") public ResponseEntity<ApiResponse<CategoriaTicketResponse>> buscar(@PathVariable Long id) { return ResponseEntity.ok(ApiResponse.ok("Categoría encontrada", service.buscar(id))); }
    @PostMapping public ResponseEntity<ApiResponse<CategoriaTicketResponse>> crear(@Valid @RequestBody CategoriaTicketRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Categoría creada", service.crear(request))); }
    @PutMapping("/{id}") public ResponseEntity<ApiResponse<CategoriaTicketResponse>> actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaTicketRequest request) { return ResponseEntity.ok(ApiResponse.ok("Categoría actualizada", service.actualizar(id, request))); }
    @PatchMapping("/{id}/desactivar") public ResponseEntity<ApiResponse<Void>> desactivar(@PathVariable Long id) { service.desactivar(id); return ResponseEntity.ok(ApiResponse.ok("Categoría desactivada", null)); }
}
