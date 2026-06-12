package cl.vetnova.notificaciones.service;

import cl.vetnova.notificaciones.model.Notificacion;
import cl.vetnova.notificaciones.repository.NotificacionRepository;
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
public class NotificacionService {
    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    @Autowired
    private NotificacionRepository notificacionRepository;

    public Notificacion crear(Notificacion notificacion) {
        log.info("event=crear_notificacion destinatarioId={} motivo={}", notificacion.getDestinatarioId(), notificacion.getMotivo());
        notificacion.setEstado("PENDIENTE");
        notificacion.setIntentos(0);
        return notificacionRepository.save(notificacion);
    }

    public List<Notificacion> listar() {
        return notificacionRepository.findAll();
    }

    public Notificacion buscarPorId(Long id) {
        return notificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada con id " + id));
    }

    public List<Notificacion> buscarPorDestinatario(Long destinatarioId) {
        return notificacionRepository.findByDestinatarioId(destinatarioId);
    }

    public Notificacion modificar(Long id, Notificacion datos) {
        Notificacion existente = notificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada con id " + id));
        existente.setDestinatarioId(datos.getDestinatarioId());
        existente.setTipoDestinatario(datos.getTipoDestinatario());
        existente.setCanal(datos.getCanal());
        existente.setMotivo(datos.getMotivo());
        existente.setAsunto(datos.getAsunto());
        existente.setMensaje(datos.getMensaje());
        existente.setEstado(datos.getEstado());
        existente.setProgramadaPara(datos.getProgramadaPara());
        return notificacionRepository.save(existente);
    }

    public Notificacion enviar(Long id) {
        log.info("event=enviar_notificacion notificacionId={}", id);
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada con id " + id));
        notificacion.setEstado("ENVIADA");
        notificacion.setEnviadaEn(new Date());
        return notificacionRepository.save(notificacion);
    }

    public Notificacion reintentar(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada con id " + id));
        notificacion.setIntentos(notificacion.getIntentos() + 1);
        notificacion.setEstado("REINTENTANDO");
        return notificacionRepository.save(notificacion);
    }

    public Notificacion marcarLeida(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada con id " + id));
        notificacion.setEstado("LEIDA");
        return notificacionRepository.save(notificacion);
    }

    public void eliminar(Long id) {
        if (!notificacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notificación no encontrada con id " + id);
        }
        notificacionRepository.deleteById(id);
    }
}
