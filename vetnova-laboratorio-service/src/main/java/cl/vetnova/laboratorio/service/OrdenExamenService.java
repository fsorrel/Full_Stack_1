package cl.vetnova.laboratorio.service;

import cl.vetnova.laboratorio.client.AuthClient;
import cl.vetnova.laboratorio.dto.*;
import cl.vetnova.laboratorio.exception.BusinessRuleException;
import cl.vetnova.laboratorio.exception.ResourceNotFoundException;
import cl.vetnova.laboratorio.model.*;
import cl.vetnova.laboratorio.repository.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdenExamenService {
    private static final Logger log = LoggerFactory.getLogger(OrdenExamenService.class);
    private final OrdenExamenRepository ordenRepository;
    private final TipoExamenService tipoExamenService;
    private final MuestraRepository muestraRepository;
    private final ProcesamientoRepository procesamientoRepository;
    private final ResultadoExamenRepository resultadoRepository;
    private final AuthClient authClient;
    public OrdenExamenService(OrdenExamenRepository ordenRepository, TipoExamenService tipoExamenService, MuestraRepository muestraRepository, ProcesamientoRepository procesamientoRepository, ResultadoExamenRepository resultadoRepository, AuthClient authClient) {
        this.ordenRepository = ordenRepository; this.tipoExamenService = tipoExamenService; this.muestraRepository = muestraRepository; this.procesamientoRepository = procesamientoRepository; this.resultadoRepository = resultadoRepository; this.authClient = authClient;
    }
    @Transactional(readOnly = true)
    public List<OrdenExamenResponse> listar(String estado) { List<OrdenExamen> ordenes = estado == null ? ordenRepository.findAll() : ordenRepository.findByEstadoIgnoreCase(estado); return ordenes.stream().map(OrdenExamenResponse::from).toList(); }
    @Transactional(readOnly = true)
    public OrdenExamen buscarEntidad(Long id) { return ordenRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Orden de examen no encontrada: " + id)); }
    @Transactional(readOnly = true)
    public OrdenExamenResponse buscar(Long id) { return OrdenExamenResponse.from(buscarEntidad(id)); }

    @Transactional
    public OrdenExamenResponse crear(CrearOrdenExamenRequest request) {
        validarUsuarioRemoto(request.veterinarioId(), "veterinario");
        validarUsuarioRemoto(request.recepcionistaId(), "recepcionista");
        TipoExamen tipo = tipoExamenService.buscarEntidad(request.tipoExamenId());
        if (!Boolean.TRUE.equals(tipo.getActivo())) throw new BusinessRuleException("El tipo de examen está desactivado");
        OrdenExamen orden = new OrdenExamen(request.mascotaId(), request.veterinarioId(), request.recepcionistaId(), tipo, request.descripcion(), request.sucursal());
        OrdenExamen guardada = ordenRepository.save(orden);
        log.info("event=orden_examen_created ordenId={} mascotaId={} tipoExamenId={}", guardada.getId(), guardada.getMascotaId(), tipo.getId());
        return OrdenExamenResponse.from(guardada);
    }

    @Transactional
    public OrdenExamenResponse programar(Long id, ProgramarOrdenRequest request) {
        OrdenExamen orden = buscarEntidad(id); validarNoFinalizada(orden); orden.programar(request.fechaProgramada()); ordenRepository.save(orden); log.info("event=orden_examen_programmed ordenId={} fecha={}", id, request.fechaProgramada()); return OrdenExamenResponse.from(orden);
    }

    @Transactional
    public MuestraResponse registrarMuestra(Long ordenId, RegistrarMuestraRequest request) {
        OrdenExamen orden = buscarEntidad(ordenId);
        validarNoFinalizada(orden);
        if (!Boolean.TRUE.equals(orden.getTipoExamen().getRequiereMuestra())) throw new BusinessRuleException("El tipo de examen no requiere muestra");
        if (orden.getMuestra() != null) throw new BusinessRuleException("La orden ya tiene una muestra registrada");
        if (muestraRepository.existsByCodigoMuestra(request.codigoMuestra())) throw new BusinessRuleException("Ya existe una muestra con ese código");
        Muestra muestra = new Muestra(orden, request.tipo(), request.descripcion(), request.codigoMuestra(), request.responsableRecepcion());
        Muestra guardada = muestraRepository.save(muestra); orden.setMuestra(guardada); orden.marcarEnProceso(); ordenRepository.save(orden);
        log.info("event=muestra_registered ordenId={} muestraId={} codigo={}", ordenId, guardada.getId(), guardada.getCodigoMuestra());
        return MuestraResponse.from(guardada);
    }

    @Transactional
    public ProcesamientoResponse iniciarProcesamiento(Long ordenId, IniciarProcesamientoRequest request) {
        OrdenExamen orden = buscarEntidad(ordenId); validarNoFinalizada(orden); validarUsuarioRemoto(request.tecnicoId(), "técnico");
        if (orden.getMuestra() == null) throw new BusinessRuleException("Debe registrar una muestra antes de iniciar procesamiento");
        if (orden.getMuestra().getProcesamiento() != null) throw new BusinessRuleException("La muestra ya tiene procesamiento iniciado");
        Procesamiento procesamiento = new Procesamiento(orden.getMuestra(), request.tecnicoId(), request.metodologia(), request.observaciones());
        Procesamiento guardado = procesamientoRepository.save(procesamiento); orden.getMuestra().setProcesamiento(guardado); orden.getMuestra().setEstadoProcesamiento("EN_PROCESO"); muestraRepository.save(orden.getMuestra());
        log.info("event=procesamiento_started ordenId={} muestraId={} tecnicoId={}", ordenId, orden.getMuestra().getId(), request.tecnicoId());
        return ProcesamientoResponse.from(guardado);
    }

    @Transactional
    public ProcesamientoResponse completarProcesamiento(Long ordenId, CompletarProcesamientoRequest request) {
        OrdenExamen orden = buscarEntidad(ordenId);
        if (orden.getMuestra() == null || orden.getMuestra().getProcesamiento() == null) throw new BusinessRuleException("No existe procesamiento iniciado para esta orden");
        Procesamiento p = orden.getMuestra().getProcesamiento();
        if ("COMPLETADO".equals(p.getEstado())) throw new BusinessRuleException("El procesamiento ya está completado");
        p.completar(request.observaciones()); orden.getMuestra().setEstadoProcesamiento("COMPLETADA"); procesamientoRepository.save(p); muestraRepository.save(orden.getMuestra());
        log.info("event=procesamiento_completed ordenId={} procesamientoId={}", ordenId, p.getId());
        return ProcesamientoResponse.from(p);
    }

    @Transactional
    public ResultadoExamenResponse registrarResultado(Long ordenId, RegistrarResultadoRequest request) {
        OrdenExamen orden = buscarEntidad(ordenId);
        if (orden.getResultado() != null) throw new BusinessRuleException("La orden ya tiene resultado registrado");
        validarUsuarioRemoto(request.tecnicoId(), "técnico");
        if (Boolean.TRUE.equals(orden.getTipoExamen().getRequiereMuestra())) {
            if (orden.getMuestra() == null || orden.getMuestra().getProcesamiento() == null || !"COMPLETADO".equals(orden.getMuestra().getProcesamiento().getEstado())) {
                throw new BusinessRuleException("Para registrar resultado, la muestra debe estar procesada y completada");
            }
        }
        ResultadoExamen resultado = new ResultadoExamen(orden, orden.getMuestra(), request.tecnicoId(), request.resultado(), request.observaciones(), request.interpretacion(), request.disponible() == null || request.disponible());
        ResultadoExamen guardado = resultadoRepository.save(resultado); orden.setResultado(guardado); orden.marcarCompletada(); ordenRepository.save(orden);
        log.info("event=resultado_registered ordenId={} resultadoId={} disponible={}", ordenId, guardado.getId(), guardado.getDisponible());
        return ResultadoExamenResponse.from(guardado);
    }

    @Transactional
    public OrdenExamenResponse cancelar(Long id, String motivo) {
        OrdenExamen orden = buscarEntidad(id); validarNoFinalizada(orden); orden.cancelar(); ordenRepository.save(orden); log.info("event=orden_examen_cancelled ordenId={} motivo={}", id, motivo); return OrdenExamenResponse.from(orden);
    }

    private void validarNoFinalizada(OrdenExamen orden) {
        if ("CANCELADA".equals(orden.getEstado()) || "COMPLETADA".equals(orden.getEstado())) throw new BusinessRuleException("La orden ya está finalizada: " + orden.getEstado());
    }
    private void validarUsuarioRemoto(Long usuarioId, String etiqueta) {
        if (!authClient.usuarioExiste(usuarioId)) throw new BusinessRuleException("El " + etiqueta + " informado no existe en Auth Service: " + usuarioId);
    }
}
