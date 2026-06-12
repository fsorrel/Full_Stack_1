package cl.vetnova.reportes.service;

import cl.vetnova.reportes.model.ReporteStock;
import cl.vetnova.reportes.repository.ReporteStockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.reportes.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class ReporteStockService {
    private static final Logger log = LoggerFactory.getLogger(ReporteStockService.class);

    @Autowired
    private ReporteStockRepository reporteStockRepository;

    // GET todos
    public List<ReporteStock> listar() {
        return reporteStockRepository.findAll();
    }

    // GET por ID
    public ReporteStock obtenerPorId(Long id) {
        return reporteStockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReporteStock no encontrado con id " + id));
    }

    // GET por reporteId
    public ReporteStock obtenerPorReporteId(Long reporteId) {
        return reporteStockRepository.findByReporteId(reporteId)
                .orElseThrow(() -> new ResourceNotFoundException("ReporteStock no encontrado con id " + reporteId));
    }

    // POST - crear
    public ReporteStock guardar(ReporteStock reporteStock) {
        log.info("event=guardar_reportestock");
        calcularIndicadores(reporteStock);
        return reporteStockRepository.save(reporteStock);
    }

    // PUT - actualizar
    public ReporteStock actualizar(Long id, ReporteStock datos) {
        ReporteStock existente = reporteStockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReporteStock no encontrado con id: " + id));

        existente.setReporteId(datos.getReporteId());
        existente.setProductosConStockCritico(datos.getProductosConStockCritico());
        existente.setProductosEnTransito(datos.getProductosEnTransito());
        existente.setStockPorSucursal(datos.getStockPorSucursal());
        existente.setMovimientosRecientes(datos.getMovimientosRecientes());

        calcularIndicadores(existente);
        return reporteStockRepository.save(existente);
    }

    // DELETE
    public void eliminar(Long id) {
        log.info("event=eliminar_reportestock id={}", id);
        if (!reporteStockRepository.existsById(id)) {
            throw new ResourceNotFoundException("ReporteStock no encontrado con id: " + id);
        }
        reporteStockRepository.deleteById(id);
    }

    // Calcula el total de productos en transito desde stockPorSucursal si está disponible
    private void calcularIndicadores(ReporteStock r) {
        if (r.getStockPorSucursal() != null && !r.getStockPorSucursal().isEmpty()) {
            // Productos en tránsito = suma de todos los movimientos recientes negativos (salidas)
            if (r.getMovimientosRecientes() != null) {
                int enTransito = (int) r.getMovimientosRecientes().values().stream()
                        .filter(v -> v < 0)
                        .count();
                r.setProductosEnTransito(enTransito);
            }
        }
    }
}
