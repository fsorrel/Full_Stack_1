package cl.vetnova.reportes.controller;

import cl.vetnova.reportes.model.ReporteAtencion;
import cl.vetnova.reportes.service.ReporteAtencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes-atencion")
public class ReporteAtencionController {

    @Autowired
    private ReporteAtencionService reporteAtencionService;

    // GET /api/v1/reportes-atencion
    @GetMapping
    public ResponseEntity<List<ReporteAtencion>> getAll() {
        return ResponseEntity.ok(reporteAtencionService.listar());
    }

    // GET /api/v1/reportes-atencion/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ReporteAtencion> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reporteAtencionService.obtenerPorId(id));
    }

    // GET /api/v1/reportes-atencion/reporte/{reporteId}
    @GetMapping("/reporte/{reporteId}")
    public ResponseEntity<ReporteAtencion> getByReporteId(@PathVariable Long reporteId) {
        return ResponseEntity.ok(reporteAtencionService.obtenerPorReporteId(reporteId));
    }

    // POST /api/v1/reportes-atencion
    @PostMapping
    public ResponseEntity<ReporteAtencion> create(@Valid @RequestBody ReporteAtencion reporteAtencion) {
        ReporteAtencion creado = reporteAtencionService.guardar(reporteAtencion);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /api/v1/reportes-atencion/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ReporteAtencion> update(@PathVariable Long id, @RequestBody ReporteAtencion reporteAtencion) {
        return ResponseEntity.ok(reporteAtencionService.actualizar(id, reporteAtencion));
    }

    // DELETE /api/v1/reportes-atencion/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reporteAtencionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
