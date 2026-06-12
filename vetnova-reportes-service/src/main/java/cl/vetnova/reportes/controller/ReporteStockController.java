package cl.vetnova.reportes.controller;

import cl.vetnova.reportes.model.ReporteStock;
import cl.vetnova.reportes.service.ReporteStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes-stock")
public class ReporteStockController {

    @Autowired
    private ReporteStockService reporteStockService;

    // GET /api/v1/reportes-stock
    @GetMapping
    public ResponseEntity<List<ReporteStock>> getAll() {
        return ResponseEntity.ok(reporteStockService.listar());
    }

    // GET /api/v1/reportes-stock/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ReporteStock> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reporteStockService.obtenerPorId(id));
    }

    // GET /api/v1/reportes-stock/reporte/{reporteId}
    @GetMapping("/reporte/{reporteId}")
    public ResponseEntity<ReporteStock> getByReporteId(@PathVariable Long reporteId) {
        return ResponseEntity.ok(reporteStockService.obtenerPorReporteId(reporteId));
    }

    // POST /api/v1/reportes-stock
    @PostMapping
    public ResponseEntity<ReporteStock> create(@Valid @RequestBody ReporteStock reporteStock) {
        ReporteStock creado = reporteStockService.guardar(reporteStock);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /api/v1/reportes-stock/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ReporteStock> update(@PathVariable Long id, @RequestBody ReporteStock reporteStock) {
        return ResponseEntity.ok(reporteStockService.actualizar(id, reporteStock));
    }

    // DELETE /api/v1/reportes-stock/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reporteStockService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
