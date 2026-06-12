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

import cl.vetnova.fichaclinica.model.Vacuna;
import cl.vetnova.fichaclinica.service.VacunaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/vacunas")

public class VacunaController {

    @Autowired
    private VacunaService vacunaService;

    @PostMapping
    public ResponseEntity<Vacuna> crear(@Valid @RequestBody Vacuna vacuna){
        return ResponseEntity.status(HttpStatus.CREATED).body(vacunaService.crear(vacuna));
    }

    @GetMapping
    public ResponseEntity<List<Vacuna>> listar(){
        return ResponseEntity.ok(vacunaService.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        vacunaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}