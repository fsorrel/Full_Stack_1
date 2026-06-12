package cl.vetnova.notificaciones.controller;

import cl.vetnova.notificaciones.model.HistorialMensaje;
import cl.vetnova.notificaciones.service.HistorialMensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/historial")
public class HistorialMensajeController {

    @Autowired
    private HistorialMensajeService historialMensajeService;

    @PostMapping
    public ResponseEntity<HistorialMensaje> registrar(@Valid @RequestBody HistorialMensaje historial) {
        return ResponseEntity.ok(historialMensajeService.registrar(historial));
    }

    @GetMapping
    public ResponseEntity<List<HistorialMensaje>> listar() {
        return ResponseEntity.ok(historialMensajeService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialMensaje> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(historialMensajeService.buscarPorId(id));
    }

    @GetMapping("/destinatario/{destinatarioId}")
    public ResponseEntity<List<HistorialMensaje>> getHistorialPorDestinatario(@PathVariable Long destinatarioId) {
        return ResponseEntity.ok(historialMensajeService.getHistorialPorDestinatario(destinatarioId));
    }

    @GetMapping("/notificacion/{notificacionId}")
    public ResponseEntity<List<HistorialMensaje>> getHistorialPorNotificacion(@PathVariable Long notificacionId) {
        return ResponseEntity.ok(historialMensajeService.getHistorialPorNotificacion(notificacionId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialMensaje> modificar(@PathVariable Long id, @RequestBody HistorialMensaje historial) {
        return ResponseEntity.ok(historialMensajeService.modificar(id, historial));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        historialMensajeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
