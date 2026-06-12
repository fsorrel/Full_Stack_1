package cl.vetnova.notificaciones.service;

import cl.vetnova.notificaciones.model.PlantillaMensaje;
import cl.vetnova.notificaciones.repository.PlantillaMensajeRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.notificaciones.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PlantillaMensajeService {
    private static final Logger log = LoggerFactory.getLogger(PlantillaMensajeService.class);

    @Autowired
    private PlantillaMensajeRepository plantillaMensajeRepository;

    public PlantillaMensaje crear(PlantillaMensaje plantilla) {
        log.info("event=crear_plantillamensaje");
        return plantillaMensajeRepository.save(plantilla);
    }

    public List<PlantillaMensaje> listar() {
        return plantillaMensajeRepository.findAll();
    }

    public PlantillaMensaje buscarPorId(Long id) {
        return plantillaMensajeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plantilla no encontrada con id " + id));
    }

    public PlantillaMensaje modificar(Long id, PlantillaMensaje datos) {
        PlantillaMensaje existente = plantillaMensajeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plantilla no encontrada con id " + id));
        existente.setMotivo(datos.getMotivo());
        existente.setCanal(datos.getCanal());
        existente.setAsunto(datos.getAsunto());
        existente.setCuerpo(datos.getCuerpo());
        existente.setVariablesRequeridas(datos.getVariablesRequeridas());
        existente.setActiva(datos.getActiva());
        return plantillaMensajeRepository.save(existente);
    }

    // Renderiza la plantilla reemplazando las variables {{variable}} por sus valores
    public String renderizar(Long id, Map<String, String> variables) {
        PlantillaMensaje plantilla = plantillaMensajeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plantilla no encontrada con id " + id));
        String resultado = plantilla.getCuerpo();
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            resultado = resultado.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return resultado;
    }

    // Valida que todas las variables requeridas estén presentes
    public Boolean validarVariables(Long id, Map<String, String> variables) {
        PlantillaMensaje plantilla = plantillaMensajeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plantilla no encontrada con id " + id));
        if (plantilla.getVariablesRequeridas() == null) return true;
        return variables.keySet().containsAll(plantilla.getVariablesRequeridas());
    }

    public void eliminar(Long id) {
        if (!plantillaMensajeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Plantilla no encontrada con id " + id);
        }
        plantillaMensajeRepository.deleteById(id);
    }
}
