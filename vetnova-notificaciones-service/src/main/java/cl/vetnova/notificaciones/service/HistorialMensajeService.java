package cl.vetnova.notificaciones.service;

import cl.vetnova.notificaciones.model.HistorialMensaje;
import cl.vetnova.notificaciones.repository.HistorialMensajeRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.notificaciones.exception.ResourceNotFoundException;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HistorialMensajeService {
    private static final Logger log = LoggerFactory.getLogger(HistorialMensajeService.class);

    @Autowired
    private HistorialMensajeRepository historialMensajeRepository;

    public HistorialMensaje registrar(HistorialMensaje historial) {
        historial.setFecha(new Date());
        return historialMensajeRepository.save(historial);
    }

    public List<HistorialMensaje> listar() {
        return historialMensajeRepository.findAll();
    }

    public HistorialMensaje buscarPorId(Long id) {
        return historialMensajeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historial no encontrado con id " + id));
    }

    public List<HistorialMensaje> getHistorialPorDestinatario(Long destinatarioId) {
        return historialMensajeRepository.findByDestinatarioId(destinatarioId);
    }

    public List<HistorialMensaje> getHistorialPorNotificacion(Long notificacionId) {
        return historialMensajeRepository.findByNotificacionId(notificacionId);
    }

    public HistorialMensaje modificar(Long id, HistorialMensaje datos) {
        HistorialMensaje existente = historialMensajeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historial no encontrado con id " + id));
        existente.setEstado(datos.getEstado());
        existente.setDetalle(datos.getDetalle());
        return historialMensajeRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!historialMensajeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Historial no encontrado con id " + id);
        }
        historialMensajeRepository.deleteById(id);
    }
}
