package cl.vetnova.notificaciones.controller;

import cl.vetnova.notificaciones.model.PlantillaMensaje;
import cl.vetnova.notificaciones.service.PlantillaMensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/plantillas")
public class PlantillaMensajeController {

    @Autowired
    private PlantillaMensajeService plantillaMensajeService;

    @PostMapping
    public ResponseEntity<PlantillaMensaje> crear(@Valid @RequestBody PlantillaMensaje plantilla) {
        return ResponseEntity.status(HttpStatus.CREATED).body(plantillaMensajeService.crear(plantilla));
    }

    @GetMapping
    public ResponseEntity<List<PlantillaMensaje>> listar() {
        return ResponseEntity.ok(plantillaMensajeService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantillaMensaje> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(plantillaMensajeService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantillaMensaje> modificar(@PathVariable Long id, @RequestBody PlantillaMensaje plantilla) {
        return ResponseEntity.ok(plantillaMensajeService.modificar(id, plantilla));
    }

    @PostMapping("/{id}/renderizar")
    public ResponseEntity<String> renderizar(@PathVariable Long id, @RequestBody Map<String, String> variables) {
        return ResponseEntity.ok(plantillaMensajeService.renderizar(id, variables));
    }

    @PostMapping("/{id}/validar-variables")
    public ResponseEntity<Boolean> validarVariables(@PathVariable Long id, @RequestBody Map<String, String> variables) {
        return ResponseEntity.ok(plantillaMensajeService.validarVariables(id, variables));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        plantillaMensajeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
