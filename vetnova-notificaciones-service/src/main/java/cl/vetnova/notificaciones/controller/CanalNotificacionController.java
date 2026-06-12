package cl.vetnova.notificaciones.controller;

import cl.vetnova.notificaciones.model.CanalNotificacion;
import cl.vetnova.notificaciones.service.CanalNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/canales")
public class CanalNotificacionController {

    @Autowired
    private CanalNotificacionService canalNotificacionService;

    @PostMapping
    public ResponseEntity<CanalNotificacion> crear(@Valid @RequestBody CanalNotificacion canal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(canalNotificacionService.crear(canal));
    }

    @GetMapping
    public ResponseEntity<List<CanalNotificacion>> listar() {
        return ResponseEntity.ok(canalNotificacionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CanalNotificacion> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(canalNotificacionService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CanalNotificacion> modificar(@PathVariable Long id, @RequestBody CanalNotificacion canal) {
        return ResponseEntity.ok(canalNotificacionService.modificar(id, canal));
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<CanalNotificacion> activar(@PathVariable Long id) {
        return ResponseEntity.ok(canalNotificacionService.activar(id));
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<CanalNotificacion> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(canalNotificacionService.desactivar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        canalNotificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
