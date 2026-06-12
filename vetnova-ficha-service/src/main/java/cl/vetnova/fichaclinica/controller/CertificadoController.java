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

import cl.vetnova.fichaclinica.model.Certificado;
import cl.vetnova.fichaclinica.service.CertificadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/certificados")

public class CertificadoController {

    @Autowired
    private CertificadoService certificadoService;

    @PostMapping
    public ResponseEntity<Certificado> crear(@Valid @RequestBody Certificado certificado){
        return ResponseEntity.status(HttpStatus.CREATED).body(certificadoService.crear(certificado));
    }

    @GetMapping
    public ResponseEntity<List<Certificado>> listar(){
        return ResponseEntity.ok(certificadoService.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        certificadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}