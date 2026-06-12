package cl.vetnova.ventas.controller;

import cl.vetnova.ventas.dto.CambiarEstadoRequest;
import cl.vetnova.ventas.dto.CrearOrdenRequest;
import cl.vetnova.ventas.dto.OrdenResponse;
import cl.vetnova.ventas.dto.RegistrarPagoRequest;
import cl.vetnova.ventas.service.OrdenService;
import cl.vetnova.ventas.service.PagoService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ordenes")
public class OrdenController {

    private final OrdenService ordenService;
    private final PagoService pagoService;

    public OrdenController(OrdenService ordenService, PagoService pagoService) {
        this.ordenService = ordenService;
        this.pagoService = pagoService;
    }

    @PostMapping
    public ResponseEntity<OrdenResponse> crear(@Valid @RequestBody CrearOrdenRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenService.crearOrden(request));
    }

    @GetMapping
    public ResponseEntity<List<OrdenResponse>> listar() {
        return ResponseEntity.ok(ordenService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ordenService.obtenerPorId(id));
    }

    // Consumido por Envío y Facturación para validar que la orden exista
    @GetMapping("/{id}/existe")
    public ResponseEntity<Map<String, Object>> existe(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("id", id, "existe", ordenService.existe(id)));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<OrdenResponse> cambiarEstado(@PathVariable Long id,
                                                       @Valid @RequestBody CambiarEstadoRequest request) {
        return ResponseEntity.ok(ordenService.cambiarEstado(id, request));
    }

    @PostMapping("/{id}/pagos")
    public ResponseEntity<OrdenResponse> registrarPago(@PathVariable Long id,
                                                       @Valid @RequestBody RegistrarPagoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.registrarPago(id, request));
    }
}
