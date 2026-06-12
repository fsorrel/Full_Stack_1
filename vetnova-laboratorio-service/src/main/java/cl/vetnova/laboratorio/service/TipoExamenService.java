package cl.vetnova.laboratorio.service;

import cl.vetnova.laboratorio.dto.TipoExamenRequest;
import cl.vetnova.laboratorio.dto.TipoExamenResponse;
import cl.vetnova.laboratorio.exception.BusinessRuleException;
import cl.vetnova.laboratorio.exception.ResourceNotFoundException;
import cl.vetnova.laboratorio.model.TipoExamen;
import cl.vetnova.laboratorio.repository.TipoExamenRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TipoExamenService {
    private static final Logger log = LoggerFactory.getLogger(TipoExamenService.class);
    private final TipoExamenRepository repository;
    public TipoExamenService(TipoExamenRepository repository) { this.repository = repository; }
    public List<TipoExamenResponse> listar() { return repository.findAll().stream().map(TipoExamenResponse::from).toList(); }
    public TipoExamen buscarEntidad(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tipo de examen no encontrado: " + id)); }
    public TipoExamenResponse buscar(Long id) { return TipoExamenResponse.from(buscarEntidad(id)); }
    @Transactional
    public TipoExamenResponse crear(TipoExamenRequest request) {
        if (repository.existsByNombreIgnoreCase(request.nombre())) throw new BusinessRuleException("Ya existe un tipo de examen con ese nombre");
        TipoExamen guardado = repository.save(new TipoExamen(request.nombre(), request.descripcion(), request.tiempoEstimadoHoras(), request.requiereMuestra(), request.instrucciones()));
        log.info("event=tipo_examen_created tipoExamenId={}", guardado.getId());
        return TipoExamenResponse.from(guardado);
    }
    @Transactional
    public TipoExamenResponse actualizar(Long id, TipoExamenRequest request) {
        TipoExamen t = buscarEntidad(id); t.setNombre(request.nombre()); t.setDescripcion(request.descripcion()); t.setTiempoEstimadoHoras(request.tiempoEstimadoHoras()); t.setRequiereMuestra(request.requiereMuestra()); t.setInstrucciones(request.instrucciones());
        log.info("event=tipo_examen_updated tipoExamenId={}", id);
        return TipoExamenResponse.from(repository.save(t));
    }
    @Transactional
    public void desactivar(Long id) { TipoExamen t = buscarEntidad(id); t.setActivo(false); repository.save(t); log.info("event=tipo_examen_disabled tipoExamenId={}", id); }
}
