package cl.vetnova.facturacion.controller;

import cl.vetnova.facturacion.model.Folio;
import cl.vetnova.facturacion.service.FolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/folios")
public class FolioController {

    @Autowired
    private FolioService folioService;

    // ─── CRUD ───────────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<Folio> crear(@Valid @RequestBody Folio folio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(folioService.crear(folio));
    }

    @GetMapping
    public ResponseEntity<List<Folio>> listar() {
        return ResponseEntity.ok(folioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Folio> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(folioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Folio> modificar(@PathVariable Long id, @RequestBody Folio folio) {
        return ResponseEntity.ok(folioService.modificar(id, folio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        folioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ─── CONSULTAS ───────────────────────────────────────────────────────────────

    @GetMapping("/sucursal/{sucursal}")
    public ResponseEntity<List<Folio>> porSucursal(@PathVariable String sucursal) {
        return ResponseEntity.ok(folioService.buscarPorSucursal(sucursal));
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Folio>> activos() {
        return ResponseEntity.ok(folioService.listarActivos());
    }

    // ─── ACCIONES DE DOMINIO ─────────────────────────────────────────────────────

    @PutMapping("/{id}/cargar")
    public ResponseEntity<Folio> cargar(@PathVariable Long id, @RequestBody Map<String, Integer> rango) {
        Integer desde = rango.get("folioDesde");
        Integer hasta = rango.get("folioHasta");
        return ResponseEntity.ok(folioService.cargar(id, desde, hasta));
    }

    @GetMapping("/{id}/siguiente")
    public ResponseEntity<Integer> getSiguienteFolio(@PathVariable Long id) {
        return ResponseEntity.ok(folioService.getSiguienteFolio(id));
    }

    @GetMapping("/{id}/stock-bajo")
    public ResponseEntity<Boolean> stockBajo(@PathVariable Long id) {
        return ResponseEntity.ok(folioService.stockBajo(id));
    }
}
