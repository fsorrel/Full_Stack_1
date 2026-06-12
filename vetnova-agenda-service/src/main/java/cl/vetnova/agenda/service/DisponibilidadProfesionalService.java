package cl.vetnova.agenda.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.agenda.exception.ResourceNotFoundException;
import cl.vetnova.agenda.model.DisponibilidadProfesional;
import cl.vetnova.agenda.repository.DisponibilidadProfesionalRepository;

@Service
public class DisponibilidadProfesionalService {
    private static final Logger log = LoggerFactory.getLogger(DisponibilidadProfesionalService.class);

    @Autowired
    private DisponibilidadProfesionalRepository disponibilidadRepository;

    public DisponibilidadProfesional crear(DisponibilidadProfesional disponibilidad){
        log.info("event=crear_disponibilidad veterinarioId={}", disponibilidad.getVeterinarioId());
        return disponibilidadRepository.save(disponibilidad);
    }

    public List<DisponibilidadProfesional> listar(){
        return disponibilidadRepository.findAll();
    }

    public DisponibilidadProfesional activar(Long id){
        DisponibilidadProfesional disponibilidad = buscar(id);
        disponibilidad.setActiva(true);
        return disponibilidadRepository.save(disponibilidad);
    }

    public DisponibilidadProfesional desactivar(Long id){
        DisponibilidadProfesional disponibilidad = buscar(id);
        disponibilidad.setActiva(false);
        return disponibilidadRepository.save(disponibilidad);
    }

    public void eliminar(Long id){
        log.info("event=eliminar_disponibilidad disponibilidadId={}", id);
        buscar(id);
        disponibilidadRepository.deleteById(id);
    }

    private DisponibilidadProfesional buscar(Long id){
        return disponibilidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disponibilidad no encontrada con id " + id));
    }
}
