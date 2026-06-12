package cl.vetnova.notificaciones.controller;

import cl.vetnova.notificaciones.model.ConfiguracionAlerta;
import cl.vetnova.notificaciones.service.ConfiguracionAlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/configuraciones-alerta")
public class ConfiguracionAlertaController {

    @Autowired
    private ConfiguracionAlertaService configuracionAlertaService;

    @PostMapping
    public ResponseEntity<ConfiguracionAlerta> crear(@Valid @RequestBody ConfiguracionAlerta configuracionAlerta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(configuracionAlertaService.crear(configuracionAlerta));
    }

    @GetMapping
    public ResponseEntity<List<ConfiguracionAlerta>> listar() {
        return ResponseEntity.ok(configuracionAlertaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfiguracionAlerta> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(configuracionAlertaService.buscarPorId(id));
    }

    @GetMapping("/activa/{tipo}")
    public ResponseEntity<Boolean> estaActiva(@PathVariable String tipo) {
        return ResponseEntity.ok(configuracionAlertaService.estaActiva(tipo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfiguracionAlerta> modificar(@PathVariable Long id, @RequestBody ConfiguracionAlerta configuracionAlerta) {
        return ResponseEntity.ok(configuracionAlertaService.modificar(id, configuracionAlerta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        configuracionAlertaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
