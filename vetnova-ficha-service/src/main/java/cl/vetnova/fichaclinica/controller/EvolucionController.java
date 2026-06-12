package cl.vetnova.fichaclinica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.vetnova.fichaclinica.model.Evolucion;
import cl.vetnova.fichaclinica.service.EvolucionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/evoluciones")

public class EvolucionController {

    @Autowired
    private EvolucionService evolucionService;

    @PostMapping
    public ResponseEntity<Evolucion> crear(@Valid @RequestBody Evolucion evolucion){
        return ResponseEntity.status(HttpStatus.CREATED).body(evolucionService.crear(evolucion));
    }

    @GetMapping
    public ResponseEntity<List<Evolucion>> listar(){
        return ResponseEntity.ok(evolucionService.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        evolucionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
