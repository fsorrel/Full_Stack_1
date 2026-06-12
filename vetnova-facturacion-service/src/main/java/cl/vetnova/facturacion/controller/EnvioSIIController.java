package cl.vetnova.facturacion.controller;

import cl.vetnova.facturacion.model.EnvioSII;
import cl.vetnova.facturacion.service.EnvioSIIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/envios-sii")
public class EnvioSIIController {

    @Autowired
    private EnvioSIIService envioSIIService;

    // ─── CRUD ───────────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<EnvioSII> crear(@Valid @RequestBody EnvioSII envio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(envioSIIService.crear(envio));
    }

    @GetMapping
    public ResponseEntity<List<EnvioSII>> listar() {
        return ResponseEntity.ok(envioSIIService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvioSII> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(envioSIIService.buscarPorId(id));
    }

    @GetMapping("/documento/{documentoId}")
    public ResponseEntity<EnvioSII> buscarPorDocumentoId(@PathVariable Long documentoId) {
        return ResponseEntity.ok(envioSIIService.buscarPorDocumentoId(documentoId));
    }

    @GetMapping("/reintentados")
    public ResponseEntity<List<EnvioSII>> reintentados() {
        return ResponseEntity.ok(envioSIIService.buscarReintentados());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvioSII> modificar(@PathVariable Long id, @RequestBody EnvioSII envio) {
        return ResponseEntity.ok(envioSIIService.modificar(id, envio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        envioSIIService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ─── ACCIONES DE DOMINIO ─────────────────────────────────────────────────────

    @PutMapping("/{id}/enviar")
    public ResponseEntity<EnvioSII> enviar(@PathVariable Long id) {
        return ResponseEntity.ok(envioSIIService.enviar(id));
    }

    @PutMapping("/{id}/procesar-respuesta")
    public ResponseEntity<EnvioSII> procesarRespuesta(@PathVariable Long id,
                                                      @RequestBody Map<String, String> body) {
        String xml = body.getOrDefault("xml", "");
        return ResponseEntity.ok(envioSIIService.procesarRespuesta(id, xml));
    }

    @PutMapping("/{id}/reintentar")
    public ResponseEntity<EnvioSII> reintentar(@PathVariable Long id) {
        return ResponseEntity.ok(envioSIIService.reintentar(id));
    }
}
