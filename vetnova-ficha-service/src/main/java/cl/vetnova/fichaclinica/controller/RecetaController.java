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

import cl.vetnova.fichaclinica.model.Receta;
import cl.vetnova.fichaclinica.service.RecetaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/recetas")

public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @PostMapping
    public ResponseEntity<Receta> crear(@Valid @RequestBody Receta receta){
        return ResponseEntity.status(HttpStatus.CREATED).body(recetaService.crear(receta));
    }

    @GetMapping
    public ResponseEntity<List<Receta>> listar(){
        return ResponseEntity.ok(recetaService.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        recetaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}