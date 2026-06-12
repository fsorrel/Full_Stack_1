package cl.vetnova.inventario.controller;

import cl.vetnova.inventario.dto.MovimientoStockRequest;
import cl.vetnova.inventario.dto.MovimientoStockResponse;
import cl.vetnova.inventario.dto.StockDisponibleResponse;
import cl.vetnova.inventario.service.InventarioService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    // Consultado por Ventas antes de crear una orden
    @GetMapping("/productos/{idProducto}/stock")
    public ResponseEntity<StockDisponibleResponse> consultarStock(@PathVariable Long idProducto,
                                                                  @RequestParam(required = false) Long idSucursal) {
        return ResponseEntity.ok(inventarioService.consultarStock(idProducto, idSucursal));
    }

    // Consumido por Ventas (SALIDA al confirmar orden) y por Envío (transferencias)
    @PostMapping("/movimientos")
    public ResponseEntity<MovimientoStockResponse> registrarMovimiento(@Valid @RequestBody MovimientoStockRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.registrarMovimiento(request));
    }

    @GetMapping("/movimientos")
    public ResponseEntity<List<MovimientoStockResponse>> listarMovimientos(@RequestParam(required = false) Long idProducto) {
        return ResponseEntity.ok(inventarioService.listarMovimientos(idProducto));
    }

    @GetMapping("/stock/critico")
    public ResponseEntity<List<StockDisponibleResponse>> listarStockCritico() {
        return ResponseEntity.ok(inventarioService.listarStockCritico());
    }
}
