package cl.vetnova.fichaclinica.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.fichaclinica.exception.ResourceNotFoundException;

import cl.vetnova.fichaclinica.model.Certificado;
import cl.vetnova.fichaclinica.repository.CertificadoRepository;

@Service
public class CertificadoService {
    private static final Logger log = LoggerFactory.getLogger(CertificadoService.class);

    @Autowired
    private CertificadoRepository certificadoRepository;

    public Certificado crear(Certificado certificado){
        log.info("event=emitir_certificado");
        return certificadoRepository.save(certificado);
    }

    public List<Certificado> listar(){
        return certificadoRepository.findAll();
    }

    public void eliminar(Long id){
        if (!certificadoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Certificado no encontrado con id " + id);
        }
        certificadoRepository.deleteById(id);
    }
}