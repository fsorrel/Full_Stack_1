package cl.vetnova.facturacion.controller;

import cl.vetnova.facturacion.model.DocumentoTributario;
import cl.vetnova.facturacion.service.DocumentoTributarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/documentos")
public class DocumentoTributarioController {

    @Autowired
    private DocumentoTributarioService documentoTributarioService;

    // ─── CRUD ───────────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<DocumentoTributario> crear(@Valid @RequestBody DocumentoTributario documento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentoTributarioService.crear(documento));
    }

    @GetMapping
    public ResponseEntity<List<DocumentoTributario>> listar() {
        return ResponseEntity.ok(documentoTributarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoTributario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(documentoTributarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentoTributario> modificar(@PathVariable Long id,
                                         @RequestBody DocumentoTributario documento) {
        return ResponseEntity.ok(documentoTributarioService.modificar(id, documento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        documentoTributarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ─── CONSULTAS ───────────────────────────────────────────────────────────────

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<DocumentoTributario>> porCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(documentoTributarioService.buscarPorCliente(clienteId));
    }

    @GetMapping("/orden/{ordenId}")
    public ResponseEntity<List<DocumentoTributario>> porOrden(@PathVariable Long ordenId) {
        return ResponseEntity.ok(documentoTributarioService.buscarPorOrden(ordenId));
    }

    @GetMapping("/sucursal/{sucursal}")
    public ResponseEntity<List<DocumentoTributario>> porSucursal(@PathVariable String sucursal) {
        return ResponseEntity.ok(documentoTributarioService.buscarPorSucursal(sucursal));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<DocumentoTributario>> porEstadoSII(@PathVariable String estado) {
        return ResponseEntity.ok(documentoTributarioService.buscarPorEstadoSII(estado));
    }

    // ─── ACCIONES DE DOMINIO ─────────────────────────────────────────────────────

    @PutMapping("/{id}/emitir")
    public ResponseEntity<DocumentoTributario> emitir(@PathVariable Long id) {
        return ResponseEntity.ok(documentoTributarioService.emitir(id));
    }

    @PutMapping("/{id}/anular")
    public ResponseEntity<DocumentoTributario> anular(@PathVariable Long id,
                                                       @RequestBody Map<String, String> body) {
        String motivo = body.getOrDefault("motivo", "Sin motivo especificado");
        return ResponseEntity.ok(documentoTributarioService.anular(id, motivo));
    }

    @PutMapping("/{id}/enviar-sii")
    public ResponseEntity<DocumentoTributario> enviarAlSII(@PathVariable Long id) {
        return ResponseEntity.ok(documentoTributarioService.marcarEnviadoSII(id));
    }
}
