package cl.vetnova.facturacion.service;

import cl.vetnova.facturacion.model.EnvioSII;
import cl.vetnova.facturacion.repository.EnvioSIIRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.facturacion.exception.ResourceNotFoundException;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EnvioSIIService {
    private static final Logger log = LoggerFactory.getLogger(EnvioSIIService.class);

    @Autowired
    private EnvioSIIRepository envioSIIRepository;

    // ─── CRUD ───────────────────────────────────────────────────────────────────

    public EnvioSII crear(EnvioSII envio) {
        envio.setFechaEnvio(new Date());
        envio.setEstado("ENVIADO");
        envio.setReintentado(false);
        return envioSIIRepository.save(envio);
    }

    public List<EnvioSII> listar() {
        return envioSIIRepository.findAll();
    }

    public EnvioSII buscarPorId(Long id) {
        return envioSIIRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Envío SII no encontrado con id " + id));
    }

    public EnvioSII buscarPorDocumentoId(Long documentoId) {
        return envioSIIRepository.findByDocumentoId(documentoId)
                .orElseThrow(() -> new ResourceNotFoundException("No hay envío SII para el documento " + documentoId));
    }

    public EnvioSII modificar(Long id, EnvioSII envio) {
        EnvioSII existente = envioSIIRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Envío SII no encontrado con id " + id));
        existente.setDocumentoId(envio.getDocumentoId());
        existente.setFechaEnvio(envio.getFechaEnvio());
        existente.setRespuestaCodigo(envio.getRespuestaCodigo());
        existente.setRespuestaDescripcion(envio.getRespuestaDescripcion());
        existente.setEstado(envio.getEstado());
        existente.setReintentado(envio.getReintentado());
        return envioSIIRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!envioSIIRepository.existsById(id)) {
            throw new ResourceNotFoundException("Envío SII no encontrado con id " + id);
        }
        envioSIIRepository.deleteById(id);
    }

    // ─── LÓGICA DE DOMINIO (del UML) ────────────────────────────────────────────

    /** Simula envío al SII: actualiza fecha y estado */
    public EnvioSII enviar(Long id) {
        EnvioSII envio = envioSIIRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Envío SII no encontrado con id " + id));
        envio.setFechaEnvio(new Date());
        envio.setEstado("ENVIADO");
        return envioSIIRepository.save(envio);
    }

    /** Procesa la respuesta XML del SII */
    public EnvioSII procesarRespuesta(Long id, String xml) {
        EnvioSII envio = envioSIIRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Envío SII no encontrado con id " + id));
        // En producción aquí se parsearía el XML real del SII
        if (xml != null && xml.contains("ACEPTADO")) {
        envio.setRespuestaCodigo("00");
        envio.setRespuestaDescripcion("Documento aceptado por el SII");
        envio.setEstado("ACEPTADO");
        } else {
        envio.setRespuestaCodigo("99");
        envio.setRespuestaDescripcion("Documento rechazado: " + xml);
        envio.setEstado("RECHAZADO");
        }
        return envioSIIRepository.save(envio);
    }

    /** Reintenta el envío del documento al SII */
    public EnvioSII reintentar(Long id) {
        EnvioSII envio = envioSIIRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Envío SII no encontrado con id " + id));
        envio.setFechaEnvio(new Date());
        envio.setEstado("REINTENTANDO");
        envio.setReintentado(true);
        return envioSIIRepository.save(envio);
    }

    public List<EnvioSII> buscarReintentados() {
        return envioSIIRepository.findByReintentadoTrue();
    }
}
