package cl.vetnova.fichaclinica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.fichaclinica.exception.ResourceNotFoundException;

import cl.vetnova.fichaclinica.model.Receta;
import cl.vetnova.fichaclinica.repository.RecetaRepository;

@Service
public class RecetaService {
    private static final Logger log = LoggerFactory.getLogger(RecetaService.class);

    @Autowired
    private RecetaRepository recetaRepository;

    public Receta crear(Receta receta){
        log.info("event=emitir_receta");
        return recetaRepository.save(receta);
    }

    public List<Receta> listar(){
        return recetaRepository.findAll();
    }

    public void eliminar(Long id){
        if (!recetaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Receta no encontrada con id " + id);
        }
        recetaRepository.deleteById(id);
    }
}