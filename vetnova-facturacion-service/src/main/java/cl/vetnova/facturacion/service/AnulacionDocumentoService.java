package cl.vetnova.facturacion.service;

import cl.vetnova.facturacion.model.AnulacionDocumento;
import cl.vetnova.facturacion.repository.AnulacionDocumentoRepository;
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
public class AnulacionDocumentoService {
    private static final Logger log = LoggerFactory.getLogger(AnulacionDocumentoService.class);

    @Autowired
    private AnulacionDocumentoRepository anulacionDocumentoRepository;

    // ─── CRUD ───────────────────────────────────────────────────────────────────

    public AnulacionDocumento crear(AnulacionDocumento anulacion) {
        anulacion.setFechaAnulacion(new Date());
        anulacion.setEstadoSII("PENDIENTE");
        return anulacionDocumentoRepository.save(anulacion);
    }

    public List<AnulacionDocumento> listar() {
        return anulacionDocumentoRepository.findAll();
    }

    public AnulacionDocumento buscarPorId(Long id) {
        return anulacionDocumentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anulación no encontrada con id " + id));
    }

    public AnulacionDocumento buscarPorDocumentoId(Long documentoId) {
        return anulacionDocumentoRepository.findByDocumentoId(documentoId)
                .orElseThrow(() -> new ResourceNotFoundException("No hay anulación para el documento " + documentoId));
    }

    public AnulacionDocumento modificar(Long id, AnulacionDocumento anulacion) {
        AnulacionDocumento existente = anulacionDocumentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anulación no encontrada con id " + id));
        existente.setDocumentoId(anulacion.getDocumentoId());
        existente.setAdministradorId(anulacion.getAdministradorId());
        existente.setMotivo(anulacion.getMotivo());
        existente.setFechaAnulacion(anulacion.getFechaAnulacion());
        existente.setEstadoSII(anulacion.getEstadoSII());
        return anulacionDocumentoRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!anulacionDocumentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Anulación no encontrada con id " + id);
        }
        anulacionDocumentoRepository.deleteById(id);
    }

    // ─── LÓGICA DE DOMINIO (del UML) ────────────────────────────────────────────

    /** Registra la anulación y marca como notificada al SII */
    public AnulacionDocumento registrar(Long id) {
        AnulacionDocumento anulacion = anulacionDocumentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anulación no encontrada con id " + id));
        anulacion.setEstadoSII("REGISTRADO");
        return anulacionDocumentoRepository.save(anulacion);
    }

    /** Simula la notificación al SII: cambia estado a NOTIFICADO */
    public AnulacionDocumento notificarSII(Long id) {
        AnulacionDocumento anulacion = anulacionDocumentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anulación no encontrada con id " + id));
        anulacion.setEstadoSII("NOTIFICADO");
        return anulacionDocumentoRepository.save(anulacion);
    }
}
