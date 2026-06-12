package cl.vetnova.reportes.service;

import cl.vetnova.reportes.model.Reporte;
import cl.vetnova.reportes.repository.ReporteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.reportes.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReporteService {
    private static final Logger log = LoggerFactory.getLogger(ReporteService.class);

    @Autowired
    private ReporteRepository reporteRepository;

    // GET todos
    public List<Reporte> listar() {
        return reporteRepository.findAll();
    }

    // GET por ID
    public Reporte obtenerPorId(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id " + id));
    }

    // GET por sucursal
    public List<Reporte> filtrarPorSucursal(String sucursal) {
        return reporteRepository.findBySucursal(sucursal);
    }

    // GET por tipo
    public List<Reporte> filtrarPorTipo(String tipo) {
        return reporteRepository.findByTipo(tipo);
    }

    // POST - crear
    public Reporte guardar(Reporte reporte) {
        log.info("event=guardar_reporte");
        reporte.setGeneradoEn(LocalDate.now());
        if (reporte.getEstado() == null || reporte.getEstado().isBlank()) {
            reporte.setEstado("PENDIENTE");
        }
        return reporteRepository.save(reporte);
    }

    // PUT - actualizar
    public Reporte actualizar(Long id, Reporte datos) {
        Reporte existente = reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + id));

        existente.setTipo(datos.getTipo());
        existente.setSucursal(datos.getSucursal());
        existente.setDesde(datos.getDesde());
        existente.setHasta(datos.getHasta());
        existente.setGeneradoPor(datos.getGeneradoPor());
        existente.setEstado(datos.getEstado());

        return reporteRepository.save(existente);
    }

    // DELETE
    public void eliminar(Long id) {
        log.info("event=eliminar_reporte id={}", id);
        if (!reporteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reporte no encontrado con id: " + id);
        }
        reporteRepository.deleteById(id);
    }
}
