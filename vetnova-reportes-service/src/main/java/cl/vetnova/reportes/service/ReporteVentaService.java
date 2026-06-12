package cl.vetnova.reportes.service;

import cl.vetnova.reportes.model.ReporteVenta;
import cl.vetnova.reportes.repository.ReporteVentaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.reportes.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class ReporteVentaService {
    private static final Logger log = LoggerFactory.getLogger(ReporteVentaService.class);

    @Autowired
    private ReporteVentaRepository reporteVentaRepository;

    // GET todos
    public List<ReporteVenta> listar() {
        return reporteVentaRepository.findAll();
    }

    // GET por ID
    public ReporteVenta obtenerPorId(Long id) {
        return reporteVentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReporteVenta no encontrado con id " + id));
    }

    // GET por reporteId
    public ReporteVenta obtenerPorReporteId(Long reporteId) {
        return reporteVentaRepository.findByReporteId(reporteId)
                .orElseThrow(() -> new ResourceNotFoundException("ReporteVenta no encontrado con id " + reporteId));
    }

    // POST - crear
    public ReporteVenta guardar(ReporteVenta reporteVenta) {
        log.info("event=guardar_reporteventa");
        calcularIndicadores(reporteVenta);
        return reporteVentaRepository.save(reporteVenta);
    }

    // PUT - actualizar
    public ReporteVenta actualizar(Long id, ReporteVenta datos) {
        ReporteVenta existente = reporteVentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReporteVenta no encontrado con id: " + id));

        existente.setReporteId(datos.getReporteId());
        existente.setTotalOrdenes(datos.getTotalOrdenes());
        existente.setMontoTotal(datos.getMontoTotal());
        existente.setProductosVendidos(datos.getProductosVendidos());
        existente.setVentaPorProducto(datos.getVentaPorProducto());
        existente.setVentaPorPeriodo(datos.getVentaPorPeriodo());

        calcularIndicadores(existente);
        return reporteVentaRepository.save(existente);
    }

    // DELETE
    public void eliminar(Long id) {
        log.info("event=eliminar_reporteventa id={}", id);
        if (!reporteVentaRepository.existsById(id)) {
            throw new ResourceNotFoundException("ReporteVenta no encontrado con id: " + id);
        }
        reporteVentaRepository.deleteById(id);
    }

    // Calcula monto total desde ventaPorPeriodo si está disponible
    private void calcularIndicadores(ReporteVenta r) {
        if (r.getVentaPorPeriodo() != null && !r.getVentaPorPeriodo().isEmpty()) {
            double total = r.getVentaPorPeriodo().values().stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();
            r.setMontoTotal(total);
        }
        if (r.getVentaPorProducto() != null && !r.getVentaPorProducto().isEmpty()) {
            int totalProductos = r.getVentaPorProducto().values().stream()
                    .mapToInt(Integer::intValue)
                    .sum();
            r.setProductosVendidos(totalProductos);
        }
    }
}
