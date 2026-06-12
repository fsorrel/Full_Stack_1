package cl.vetnova.facturacion.service;

import cl.vetnova.facturacion.model.DocumentoTributario;
import cl.vetnova.facturacion.repository.DocumentoTributarioRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.facturacion.client.VentasClient;
import cl.vetnova.facturacion.exception.BusinessRuleException;
import cl.vetnova.facturacion.exception.ResourceNotFoundException;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DocumentoTributarioService {
    private static final Logger log = LoggerFactory.getLogger(DocumentoTributarioService.class);

    @Autowired
    private DocumentoTributarioRepository documentoTributarioRepository;

    @Autowired
    private VentasClient ventasClient;

    // ─── CRUD ───────────────────────────────────────────────────────────────────

    public DocumentoTributario crear(DocumentoTributario documento) {
        log.info("event=crear_documento_tributario ordenId={} tipo={}", documento.getOrdenId(), documento.getTipo());
        // El documento siempre se asocia a una orden real del microservicio de Ventas
        if (!ventasClient.ordenExiste(documento.getOrdenId())) {
            throw new BusinessRuleException("No existe la orden " + documento.getOrdenId() + " en el microservicio de Ventas");
        }
        documento.setFechaEmision(new Date());
        documento.setEstadoSII("PENDIENTE");
        // Calcular IVA y total automáticamente si no vienen seteados
        if (documento.getIva() == null || documento.getIva() == 0) {
            documento.setIva(documento.getNeto() * 0.19);
        }
        if (documento.getTotal() == null || documento.getTotal() == 0) {
            documento.setTotal(documento.getNeto() + documento.getIva());
        }
        return documentoTributarioRepository.save(documento);
    }

    public List<DocumentoTributario> listar() {
        return documentoTributarioRepository.findAll();
    }

    public DocumentoTributario buscarPorId(Long id) {
        return documentoTributarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento tributario no encontrado con id " + id));
    }

    public DocumentoTributario modificar(Long id, DocumentoTributario documento) {
        DocumentoTributario existente = documentoTributarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento tributario no encontrado con id " + id));
        existente.setOrdenId(documento.getOrdenId());
        existente.setClienteId(documento.getClienteId());
        existente.setTipo(documento.getTipo());
        existente.setFolio(documento.getFolio());
        existente.setNeto(documento.getNeto());
        existente.setIva(documento.getIva());
        existente.setTotal(documento.getTotal());
        existente.setEstadoSII(documento.getEstadoSII());
        existente.setRutEmisor(documento.getRutEmisor());
        existente.setSucursal(documento.getSucursal());
        return documentoTributarioRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!documentoTributarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Documento tributario no encontrado con id " + id);
        }
        documentoTributarioRepository.deleteById(id);
    }

    // ─── CONSULTAS ESPECÍFICAS ───────────────────────────────────────────────────

    public List<DocumentoTributario> buscarPorCliente(Long clienteId) {
        return documentoTributarioRepository.findByClienteId(clienteId);
    }

    public List<DocumentoTributario> buscarPorOrden(Long ordenId) {
        return documentoTributarioRepository.findByOrdenId(ordenId);
    }

    public List<DocumentoTributario> buscarPorSucursal(String sucursal) {
        return documentoTributarioRepository.findBySucursal(sucursal);
    }

    public List<DocumentoTributario> buscarPorEstadoSII(String estado) {
        return documentoTributarioRepository.findByEstadoSII(estado);
    }

    // ─── LÓGICA DE DOMINIO (del UML) ────────────────────────────────────────────

    /** Emite el documento: cambia estado a EMITIDO */
    public DocumentoTributario emitir(Long id) {
        DocumentoTributario doc = documentoTributarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento tributario no encontrado con id " + id));
        doc.setEstadoSII("EMITIDO");
        return documentoTributarioRepository.save(doc);
    }

    /** Anula el documento: cambia estado a ANULADO */
    public DocumentoTributario anular(Long id, String motivo) {
        DocumentoTributario doc = documentoTributarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento tributario no encontrado con id " + id));
        doc.setEstadoSII("ANULADO");
        return documentoTributarioRepository.save(doc);
    }

    /** Marca el documento como enviado al SII */
    public DocumentoTributario marcarEnviadoSII(Long id) {
        DocumentoTributario doc = documentoTributarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento tributario no encontrado con id " + id));
        doc.setEstadoSII("ENVIADO_SII");
        return documentoTributarioRepository.save(doc);
    }
}
