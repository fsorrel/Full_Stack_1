package cl.vetnova.reportes.service;

import cl.vetnova.reportes.model.ReporteAtencion;
import cl.vetnova.reportes.repository.ReporteAtencionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.reportes.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class ReporteAtencionService {
    private static final Logger log = LoggerFactory.getLogger(ReporteAtencionService.class);

    @Autowired
    private ReporteAtencionRepository reporteAtencionRepository;

    // GET todos
    public List<ReporteAtencion> listar() {
        return reporteAtencionRepository.findAll();
    }

    // GET por ID
    public ReporteAtencion obtenerPorId(Long id) {
        return reporteAtencionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReporteAtencion no encontrado con id " + id));
    }

    // GET por reporteId
    public ReporteAtencion obtenerPorReporteId(Long reporteId) {
        return reporteAtencionRepository.findByReporteId(reporteId)
                .orElseThrow(() -> new ResourceNotFoundException("ReporteAtencion no encontrado con id " + reporteId));
    }

    // POST - crear
    public ReporteAtencion guardar(ReporteAtencion reporteAtencion) {
        log.info("event=guardar_reporteatencion");
        calcularIndicadores(reporteAtencion);
        return reporteAtencionRepository.save(reporteAtencion);
    }

    // PUT - actualizar
    public ReporteAtencion actualizar(Long id, ReporteAtencion datos) {
        ReporteAtencion existente = reporteAtencionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReporteAtencion no encontrado con id: " + id));

        existente.setReporteId(datos.getReporteId());
        existente.setTotalCitas(datos.getTotalCitas());
        existente.setCitasRealizadas(datos.getCitasRealizadas());
        existente.setCitasCanceladas(datos.getCitasCanceladas());
        existente.setCitasAusentes(datos.getCitasAusentes());
        existente.setAtencionPorVeterinario(datos.getAtencionPorVeterinario());
        existente.setAtencionPorServicio(datos.getAtencionPorServicio());

        calcularIndicadores(existente);
        return reporteAtencionRepository.save(existente);
    }

    // DELETE
    public void eliminar(Long id) {
        log.info("event=eliminar_reporteatencion id={}", id);
        if (!reporteAtencionRepository.existsById(id)) {
            throw new ResourceNotFoundException("ReporteAtencion no encontrado con id: " + id);
        }
        reporteAtencionRepository.deleteById(id);
    }

    // Calcula totalCitas automaticamente si los datos parciales están disponibles
    private void calcularIndicadores(ReporteAtencion r) {
        if (r.getCitasRealizadas() != null && r.getCitasCanceladas() != null && r.getCitasAusentes() != null) {
            r.setTotalCitas(r.getCitasRealizadas() + r.getCitasCanceladas() + r.getCitasAusentes());
        }
    }
}
