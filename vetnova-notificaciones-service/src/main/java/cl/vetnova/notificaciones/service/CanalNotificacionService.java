package cl.vetnova.notificaciones.service;

import cl.vetnova.notificaciones.model.CanalNotificacion;
import cl.vetnova.notificaciones.repository.CanalNotificacionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.notificaciones.exception.ResourceNotFoundException;

import java.util.List;

@Service
@Transactional
public class CanalNotificacionService {
    private static final Logger log = LoggerFactory.getLogger(CanalNotificacionService.class);

    @Autowired
    private CanalNotificacionRepository canalNotificacionRepository;

    public CanalNotificacion crear(CanalNotificacion canal) {
        log.info("event=crear_canalnotificacion");
        return canalNotificacionRepository.save(canal);
    }

    public List<CanalNotificacion> listar() {
        return canalNotificacionRepository.findAll();
    }

    public CanalNotificacion buscarPorId(Long id) {
        return canalNotificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Canal no encontrado con id " + id));
    }

    public CanalNotificacion modificar(Long id, CanalNotificacion datos) {
        CanalNotificacion existente = canalNotificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Canal no encontrado con id " + id));
        existente.setNombre(datos.getNombre());
        existente.setTipo(datos.getTipo());
        existente.setActivo(datos.getActivo());
        existente.setConfiguracion(datos.getConfiguracion());
        existente.setSucursal(datos.getSucursal());
        return canalNotificacionRepository.save(existente);
    }

    public CanalNotificacion activar(Long id) {
        CanalNotificacion canal = canalNotificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Canal no encontrado con id " + id));
        canal.setActivo(true);
        return canalNotificacionRepository.save(canal);
    }

    public CanalNotificacion desactivar(Long id) {
        CanalNotificacion canal = canalNotificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Canal no encontrado con id " + id));
        canal.setActivo(false);
        return canalNotificacionRepository.save(canal);
    }

    public void eliminar(Long id) {
        if (!canalNotificacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Canal no encontrado con id " + id);
        }
        canalNotificacionRepository.deleteById(id);
    }
}
