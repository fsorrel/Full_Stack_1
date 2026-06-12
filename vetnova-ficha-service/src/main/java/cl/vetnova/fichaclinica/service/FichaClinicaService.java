package cl.vetnova.fichaclinica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.fichaclinica.exception.ResourceNotFoundException;

import cl.vetnova.fichaclinica.model.FichaClinica;
import cl.vetnova.fichaclinica.repository.FichaClinicaRepository;

@Service
public class FichaClinicaService {
    private static final Logger log = LoggerFactory.getLogger(FichaClinicaService.class);

    @Autowired
    private FichaClinicaRepository fichaClinicaRepository;

    public FichaClinica crear(FichaClinica fichaClinica){
        log.info("event=crear_ficha mascotaId={}", fichaClinica.getMascotaId());
        return fichaClinicaRepository.save(fichaClinica);
    }

    public List<FichaClinica> listar(){
        return fichaClinicaRepository.findAll();
    }

    public void eliminar(Long id){
        if (!fichaClinicaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ficha clínica no encontrada con id " + id);
        }
        fichaClinicaRepository.deleteById(id);
    }
}