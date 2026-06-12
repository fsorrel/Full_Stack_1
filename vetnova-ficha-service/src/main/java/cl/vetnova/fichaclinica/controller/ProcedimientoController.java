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

import cl.vetnova.fichaclinica.model.Procedimiento;
import cl.vetnova.fichaclinica.service.ProcedimientoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/procedimientos")

public class ProcedimientoController {

    @Autowired
    private ProcedimientoService procedimientoService;

    @PostMapping
    public ResponseEntity<Procedimiento> crear(@Valid @RequestBody Procedimiento procedimiento){
        return ResponseEntity.status(HttpStatus.CREATED).body(procedimientoService.crear(procedimiento));
    }

    @GetMapping
    public ResponseEntity<List<Procedimiento>> listar(){
        return ResponseEntity.ok(procedimientoService.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        procedimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}