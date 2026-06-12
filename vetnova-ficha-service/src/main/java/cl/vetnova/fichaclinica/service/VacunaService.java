package cl.vetnova.fichaclinica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.fichaclinica.exception.ResourceNotFoundException;

import cl.vetnova.fichaclinica.model.Vacuna;
import cl.vetnova.fichaclinica.repository.VacunaRepository;

@Service
public class VacunaService {
    private static final Logger log = LoggerFactory.getLogger(VacunaService.class);

    @Autowired
    private VacunaRepository vacunaRepository;

    public Vacuna crear(Vacuna vacuna){
        log.info("event=registrar_vacuna");
        return vacunaRepository.save(vacuna);
    }

    public List<Vacuna> listar(){
        return vacunaRepository.findAll();
    }

    public void eliminar(Long id){
        if (!vacunaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vacuna no encontrada con id " + id);
        }
        vacunaRepository.deleteById(id);
    }
}