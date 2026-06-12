package cl.vetnova.facturacion.service;

import cl.vetnova.facturacion.model.DocumentoTributario;
import cl.vetnova.facturacion.model.ReporteTributario;
import cl.vetnova.facturacion.repository.DocumentoTributarioRepository;
import cl.vetnova.facturacion.repository.ReporteTributarioRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.facturacion.exception.ResourceNotFoundException;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReporteTributarioService {
    private static final Logger log = LoggerFactory.getLogger(ReporteTributarioService.class);

    @Autowired
    private ReporteTributarioRepository reporteTributarioRepository;

    @Autowired
    private DocumentoTributarioRepository documentoTributarioRepository;

    // ─── CRUD ───────────────────────────────────────────────────────────────────

    public ReporteTributario crear(ReporteTributario reporte) {
        reporte.setGeneradoEn(new Date());
        return reporteTributarioRepository.save(reporte);
    }

    public List<ReporteTributario> listar() {
        return reporteTributarioRepository.findAll();
    }

    public ReporteTributario buscarPorId(Long id) {
        return reporteTributarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte tributario no encontrado con id " + id));
    }

    public ReporteTributario modificar(Long id, ReporteTributario reporte) {
        ReporteTributario existente = reporteTributarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte tributario no encontrado con id " + id));
        existente.setSucursal(reporte.getSucursal());
        existente.setPeriodo(reporte.getPeriodo());
        existente.setTotalDocumentos(reporte.getTotalDocumentos());
        existente.setMontoNeto(reporte.getMontoNeto());
        existente.setMontoIva(reporte.getMontoIva());
        existente.setMontoTotal(reporte.getMontoTotal());
        existente.setGeneradoEn(new Date());
        return reporteTributarioRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!reporteTributarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reporte tributario no encontrado con id " + id);
        }
        reporteTributarioRepository.deleteById(id);
    }

    // ─── CONSULTAS ───────────────────────────────────────────────────────────────

    public List<ReporteTributario> buscarPorSucursal(String sucursal) {
        return reporteTributarioRepository.findBySucursal(sucursal);
    }

    public ReporteTributario buscarPorSucursalYPeriodo(String sucursal, String periodo) {
        return reporteTributarioRepository.findBySucursalAndPeriodo(sucursal, periodo)
                .orElseThrow(() -> new ResourceNotFoundException("No hay reporte para " + sucursal + " en el periodo " + periodo));
    }

    // ─── LÓGICA DE DOMINIO (del UML) ────────────────────────────────────────────

    /**
     * Genera un ReporteTributario a partir de los DocumentoTributario existentes
     * para una sucursal y periodo determinado.
     */
    public ReporteTributario generar(String sucursal, String periodo) {
        List<DocumentoTributario> documentos = documentoTributarioRepository.findBySucursal(sucursal);

        double totalNeto = 0, totalIva = 0, totalMonto = 0;
        int count = 0;

        for (DocumentoTributario doc : documentos) {
            // Filtrar por periodo (formato esperado "YYYY-MM")
            if (doc.getFechaEmision() != null) {
                String fechaDoc = new java.text.SimpleDateFormat("yyyy-MM")
                        .format(doc.getFechaEmision());
                if (fechaDoc.equals(periodo)) {
                    totalNeto += doc.getNeto() != null ? doc.getNeto() : 0;
                    totalIva += doc.getIva() != null ? doc.getIva() : 0;
                    totalMonto += doc.getTotal() != null ? doc.getTotal() : 0;
                    count++;
                }
            }
        }

        ReporteTributario reporte = new ReporteTributario();
        reporte.setSucursal(sucursal);
        reporte.setPeriodo(periodo);
        reporte.setTotalDocumentos(count);
        reporte.setMontoNeto(totalNeto);
        reporte.setMontoIva(totalIva);
        reporte.setMontoTotal(totalMonto);
        reporte.setGeneradoEn(new Date());

        return reporteTributarioRepository.save(reporte);
    }
}
