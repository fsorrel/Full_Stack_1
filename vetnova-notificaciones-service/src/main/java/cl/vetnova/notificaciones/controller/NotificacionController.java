package cl.vetnova.notificaciones.controller;

import cl.vetnova.notificaciones.model.Notificacion;
import cl.vetnova.notificaciones.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping
    public ResponseEntity<Notificacion> crear(@Valid @RequestBody Notificacion notificacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.crear(notificacion));
    }

    @GetMapping
    public ResponseEntity<List<Notificacion>> listar() {
        return ResponseEntity.ok(notificacionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.buscarPorId(id));
    }

    @GetMapping("/destinatario/{destinatarioId}")
    public ResponseEntity<List<Notificacion>> buscarPorDestinatario(@PathVariable Long destinatarioId) {
        return ResponseEntity.ok(notificacionService.buscarPorDestinatario(destinatarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> modificar(@PathVariable Long id, @RequestBody Notificacion notificacion) {
        return ResponseEntity.ok(notificacionService.modificar(id, notificacion));
    }

    @PutMapping("/{id}/enviar")
    public ResponseEntity<Notificacion> enviar(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.enviar(id));
    }

    @PutMapping("/{id}/reintentar")
    public ResponseEntity<Notificacion> reintentar(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.reintentar(id));
    }

    @PutMapping("/{id}/marcar-leida")
    public ResponseEntity<Notificacion> marcarLeida(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.marcarLeida(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
