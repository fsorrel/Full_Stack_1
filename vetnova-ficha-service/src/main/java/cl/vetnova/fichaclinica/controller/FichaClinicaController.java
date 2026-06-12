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

import cl.vetnova.fichaclinica.model.FichaClinica;
import cl.vetnova.fichaclinica.service.FichaClinicaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/fichas")

public class FichaClinicaController {

    @Autowired
    private FichaClinicaService fichaClinicaService;

    @PostMapping
    public ResponseEntity<FichaClinica> crear(@Valid @RequestBody FichaClinica fichaClinica){
        return ResponseEntity.status(HttpStatus.CREATED).body(fichaClinicaService.crear(fichaClinica));
    }

    @GetMapping
    public ResponseEntity<List<FichaClinica>> listar(){
        return ResponseEntity.ok(fichaClinicaService.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        fichaClinicaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
