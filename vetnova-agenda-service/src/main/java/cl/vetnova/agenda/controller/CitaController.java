package cl.vetnova.agenda.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.vetnova.agenda.model.Cita;
import cl.vetnova.agenda.service.CitaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/citas")

public class CitaController {

    @Autowired
    private CitaService citaService;

    @PostMapping
    public ResponseEntity<Cita> crear(@Valid @RequestBody Cita cita){
        return ResponseEntity.status(HttpStatus.CREATED).body(citaService.crear(cita));
    }

    @GetMapping
    public ResponseEntity<List<Cita>> listar(){
        return ResponseEntity.ok(citaService.listar());
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Cita> confirmar(@PathVariable Long id){
        return ResponseEntity.ok(citaService.confirmar(id));
    }

    @PutMapping("/{id}/reprogramar")
    public ResponseEntity<Cita> reprogramar(@PathVariable Long id,
                            @RequestParam Date nuevaFecha){
        return ResponseEntity.ok(citaService.reprogramar(id, nuevaFecha));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Cita> cancelar(@PathVariable Long id,
                         @RequestParam String motivo){
        return ResponseEntity.ok(citaService.cancelar(id, motivo));
    }

    @PutMapping("/{id}/asistencia")
    public ResponseEntity<Cita> registrarAsistencia(@PathVariable Long id){
        return ResponseEntity.ok(citaService.registrarAsistencia(id));
    }

    @PutMapping("/{id}/ausente")
    public ResponseEntity<Cita> marcarAusente(@PathVariable Long id){
        return ResponseEntity.ok(citaService.marcarAusente(id));
    }
}