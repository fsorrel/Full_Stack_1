package cl.vetnova.facturacion.controller;

import cl.vetnova.facturacion.model.ReporteTributario;
import cl.vetnova.facturacion.service.ReporteTributarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteTributarioController {

    @Autowired
    private ReporteTributarioService reporteTributarioService;

    // ─── CRUD ───────────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<ReporteTributario> crear(@Valid @RequestBody ReporteTributario reporte) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteTributarioService.crear(reporte));
    }

    @GetMapping
    public ResponseEntity<List<ReporteTributario>> listar() {
        return ResponseEntity.ok(reporteTributarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteTributario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reporteTributarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteTributario> modificar(@PathVariable Long id,
                                       @RequestBody ReporteTributario reporte) {
        return ResponseEntity.ok(reporteTributarioService.modificar(id, reporte));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reporteTributarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ─── CONSULTAS ───────────────────────────────────────────────────────────────

    @GetMapping("/sucursal/{sucursal}")
    public ResponseEntity<List<ReporteTributario>> porSucursal(@PathVariable String sucursal) {
        return ResponseEntity.ok(reporteTributarioService.buscarPorSucursal(sucursal));
    }

    @GetMapping("/sucursal/{sucursal}/periodo/{periodo}")
    public ResponseEntity<ReporteTributario> porSucursalYPeriodo(@PathVariable String sucursal,
                                                  @PathVariable String periodo) {
        return ResponseEntity.ok(reporteTributarioService.buscarPorSucursalYPeriodo(sucursal, periodo));
    }

    // ─── ACCIÓN DE DOMINIO: generar reporte automáticamente ──────────────────────

    @PostMapping("/generar")
    public ResponseEntity<ReporteTributario> generar(@RequestParam String sucursal,
                                     @RequestParam String periodo) {
        return ResponseEntity.ok(reporteTributarioService.generar(sucursal, periodo));
    }
}
