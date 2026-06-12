package cl.vetnova.reportes.controller;

import cl.vetnova.reportes.model.ReporteVenta;
import cl.vetnova.reportes.service.ReporteVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes-venta")
public class ReporteVentaController {

    @Autowired
    private ReporteVentaService reporteVentaService;

    // GET /api/v1/reportes-venta
    @GetMapping
    public ResponseEntity<List<ReporteVenta>> getAll() {
        return ResponseEntity.ok(reporteVentaService.listar());
    }

    // GET /api/v1/reportes-venta/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ReporteVenta> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reporteVentaService.obtenerPorId(id));
    }

    // GET /api/v1/reportes-venta/reporte/{reporteId}
    @GetMapping("/reporte/{reporteId}")
    public ResponseEntity<ReporteVenta> getByReporteId(@PathVariable Long reporteId) {
        return ResponseEntity.ok(reporteVentaService.obtenerPorReporteId(reporteId));
    }

    // POST /api/v1/reportes-venta
    @PostMapping
    public ResponseEntity<ReporteVenta> create(@Valid @RequestBody ReporteVenta reporteVenta) {
        ReporteVenta creado = reporteVentaService.guardar(reporteVenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /api/v1/reportes-venta/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ReporteVenta> update(@PathVariable Long id, @RequestBody ReporteVenta reporteVenta) {
        return ResponseEntity.ok(reporteVentaService.actualizar(id, reporteVenta));
    }

    // DELETE /api/v1/reportes-venta/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reporteVentaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
