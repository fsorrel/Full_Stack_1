package cl.vetnova.facturacion.controller;

import cl.vetnova.facturacion.model.AnulacionDocumento;
import cl.vetnova.facturacion.service.AnulacionDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/anulaciones")
public class AnulacionDocumentoController {

    @Autowired
    private AnulacionDocumentoService anulacionDocumentoService;

    // ─── CRUD ───────────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<AnulacionDocumento> crear(@Valid @RequestBody AnulacionDocumento anulacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(anulacionDocumentoService.crear(anulacion));
    }

    @GetMapping
    public ResponseEntity<List<AnulacionDocumento>> listar() {
        return ResponseEntity.ok(anulacionDocumentoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnulacionDocumento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(anulacionDocumentoService.buscarPorId(id));
    }

    @GetMapping("/documento/{documentoId}")
    public ResponseEntity<AnulacionDocumento> buscarPorDocumentoId(@PathVariable Long documentoId) {
        return ResponseEntity.ok(anulacionDocumentoService.buscarPorDocumentoId(documentoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnulacionDocumento> modificar(@PathVariable Long id,
                                        @RequestBody AnulacionDocumento anulacion) {
        return ResponseEntity.ok(anulacionDocumentoService.modificar(id, anulacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        anulacionDocumentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ─── ACCIONES DE DOMINIO ─────────────────────────────────────────────────────

    @PutMapping("/{id}/registrar")
    public ResponseEntity<AnulacionDocumento> registrar(@PathVariable Long id) {
        return ResponseEntity.ok(anulacionDocumentoService.registrar(id));
    }

    @PutMapping("/{id}/notificar-sii")
    public ResponseEntity<AnulacionDocumento> notificarSII(@PathVariable Long id) {
        return ResponseEntity.ok(anulacionDocumentoService.notificarSII(id));
    }
}
