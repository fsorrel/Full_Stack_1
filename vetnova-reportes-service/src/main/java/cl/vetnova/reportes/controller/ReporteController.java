package cl.vetnova.reportes.controller;

import cl.vetnova.reportes.model.Reporte;
import cl.vetnova.reportes.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    // GET /api/v1/reportes
    @GetMapping
    public ResponseEntity<List<Reporte>> getAll() {
        return ResponseEntity.ok(reporteService.listar());
    }

    // GET /api/v1/reportes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Reporte> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reporteService.obtenerPorId(id));
    }

    // GET /api/v1/reportes/sucursal/{sucursal}
    @GetMapping("/sucursal/{sucursal}")
    public ResponseEntity<List<Reporte>> getBySucursal(@PathVariable String sucursal) {
        return ResponseEntity.ok(reporteService.filtrarPorSucursal(sucursal));
    }

    // GET /api/v1/reportes/tipo/{tipo}
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Reporte>> getByTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(reporteService.filtrarPorTipo(tipo));
    }

    // POST /api/v1/reportes
    @PostMapping
    public ResponseEntity<Reporte> create(@Valid @RequestBody Reporte reporte) {
        Reporte creado = reporteService.guardar(reporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /api/v1/reportes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Reporte> update(@PathVariable Long id, @RequestBody Reporte reporte) {
        return ResponseEntity.ok(reporteService.actualizar(id, reporte));
    }

    // DELETE /api/v1/reportes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
