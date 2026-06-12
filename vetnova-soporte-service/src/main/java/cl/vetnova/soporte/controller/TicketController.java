package cl.vetnova.soporte.controller;

import cl.vetnova.soporte.dto.*;
import cl.vetnova.soporte.service.TicketService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService service;
    public TicketController(TicketService service) { this.service = service; }
    @GetMapping public ResponseEntity<ApiResponse<List<TicketResponse>>> listar(@RequestParam(required = false) String estado) { return ResponseEntity.ok(ApiResponse.ok("Tickets encontrados", service.listar(estado))); }
    @GetMapping("/{id}") public ResponseEntity<ApiResponse<TicketResponse>> buscar(@PathVariable Long id) { return ResponseEntity.ok(ApiResponse.ok("Ticket encontrado", service.buscar(id))); }
    @PostMapping public ResponseEntity<ApiResponse<TicketResponse>> crear(@Valid @RequestBody CrearTicketRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Ticket creado", service.crear(request))); }
    @PutMapping("/{id}") public ResponseEntity<ApiResponse<TicketResponse>> actualizar(@PathVariable Long id, @Valid @RequestBody ActualizarTicketRequest request) { return ResponseEntity.ok(ApiResponse.ok("Ticket actualizado", service.actualizar(id, request))); }
    @PostMapping("/{id}/respuestas") public ResponseEntity<ApiResponse<RespuestaTicketResponse>> responder(@PathVariable Long id, @Valid @RequestBody ResponderTicketRequest request) { return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Respuesta registrada", service.responder(id, request))); }
    @PostMapping("/{id}/derivar") public ResponseEntity<ApiResponse<TicketResponse>> derivar(@PathVariable Long id, @Valid @RequestBody DerivarTicketRequest request) { return ResponseEntity.ok(ApiResponse.ok("Ticket derivado", service.derivar(id, request))); }
    @PostMapping("/{id}/escalar") public ResponseEntity<ApiResponse<TicketResponse>> escalar(@PathVariable Long id, @Valid @RequestBody EscalarTicketRequest request) { return ResponseEntity.ok(ApiResponse.ok("Ticket escalado", service.escalar(id, request))); }
    @PostMapping("/{id}/cerrar") public ResponseEntity<ApiResponse<TicketResponse>> cerrar(@PathVariable Long id, @Valid @RequestBody CerrarTicketRequest request) { return ResponseEntity.ok(ApiResponse.ok("Ticket cerrado", service.cerrar(id, request))); }
}
