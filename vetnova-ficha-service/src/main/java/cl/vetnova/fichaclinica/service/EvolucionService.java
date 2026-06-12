package cl.vetnova.fichaclinica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.fichaclinica.exception.ResourceNotFoundException;

import cl.vetnova.fichaclinica.model.Evolucion;
import cl.vetnova.fichaclinica.repository.EvolucionRepository;

@Service
public class EvolucionService {
    private static final Logger log = LoggerFactory.getLogger(EvolucionService.class);

    @Autowired
    private EvolucionRepository evolucionRepository;

    public Evolucion crear(Evolucion evolucion){
        log.info("event=registrar_evolucion");
        return evolucionRepository.save(evolucion);
    }

    public List<Evolucion> listar(){
        return evolucionRepository.findAll();
    }

    public void eliminar(Long id){
        if (!evolucionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Evolución no encontrada con id " + id);
        }
        evolucionRepository.deleteById(id);
    }
}