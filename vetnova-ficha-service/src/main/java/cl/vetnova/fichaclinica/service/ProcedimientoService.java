package cl.vetnova.fichaclinica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.fichaclinica.exception.ResourceNotFoundException;

import cl.vetnova.fichaclinica.model.Procedimiento;
import cl.vetnova.fichaclinica.repository.ProcedimientoRepository;

@Service
public class ProcedimientoService {
    private static final Logger log = LoggerFactory.getLogger(ProcedimientoService.class);

    @Autowired
    private ProcedimientoRepository procedimientoRepository;

    public Procedimiento crear(Procedimiento procedimiento){
        log.info("event=registrar_procedimiento");
        return procedimientoRepository.save(procedimiento);
    }

    public List<Procedimiento> listar(){
        return procedimientoRepository.findAll();
    }

    public void eliminar(Long id){
        if (!procedimientoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Procedimiento no encontrado con id " + id);
        }
        procedimientoRepository.deleteById(id);
    }
}