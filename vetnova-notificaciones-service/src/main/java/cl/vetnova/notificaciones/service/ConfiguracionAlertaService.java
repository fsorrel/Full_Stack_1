package cl.vetnova.notificaciones.service;

import cl.vetnova.notificaciones.model.ConfiguracionAlerta;
import cl.vetnova.notificaciones.repository.ConfiguracionAlertaRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.notificaciones.exception.ResourceNotFoundException;

import java.util.List;

@Service
@Transactional
public class ConfiguracionAlertaService {
    private static final Logger log = LoggerFactory.getLogger(ConfiguracionAlertaService.class);

    @Autowired
    private ConfiguracionAlertaRepository configuracionAlertaRepository;

    public ConfiguracionAlerta crear(ConfiguracionAlerta configuracionAlerta) {
        log.info("event=crear_configuracionalerta");
        return configuracionAlertaRepository.save(configuracionAlerta);
    }

    public List<ConfiguracionAlerta> listar() {
        return configuracionAlertaRepository.findAll();
    }

    public ConfiguracionAlerta buscarPorId(Long id) {
        return configuracionAlertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuración de alerta no encontrada con id " + id));
    }

    public ConfiguracionAlerta modificar(Long id, ConfiguracionAlerta datos) {
        ConfiguracionAlerta existente = configuracionAlertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuración de alerta no encontrada con id " + id));
        existente.setTipo(datos.getTipo());
        existente.setActiva(datos.getActiva());
        existente.setAnticipacionHoras(datos.getAnticipacionHoras());
        existente.setCanalesActivos(datos.getCanalesActivos());
        existente.setSucursal(datos.getSucursal());
        return configuracionAlertaRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!configuracionAlertaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Configuración de alerta no encontrada con id " + id);
        }
        configuracionAlertaRepository.deleteById(id);
    }

    public Boolean estaActiva(String tipo) {
        List<ConfiguracionAlerta> configs = configuracionAlertaRepository.findByTipo(tipo);
        return configs.stream().anyMatch(ConfiguracionAlerta::getActiva);
    }
}
