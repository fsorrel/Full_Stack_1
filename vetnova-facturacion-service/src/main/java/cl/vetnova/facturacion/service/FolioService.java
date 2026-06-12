package cl.vetnova.facturacion.service;

import cl.vetnova.facturacion.model.Folio;
import cl.vetnova.facturacion.repository.FolioRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.vetnova.facturacion.exception.BusinessRuleException;
import cl.vetnova.facturacion.exception.ResourceNotFoundException;

import java.util.List;

@Service
@Transactional
public class FolioService {
    private static final Logger log = LoggerFactory.getLogger(FolioService.class);

    @Autowired
    private FolioRepository folioRepository;

    // ─── CRUD ───────────────────────────────────────────────────────────────────

    public Folio crear(Folio folio) {
        folio.setFolioActual(folio.getFolioDesde());
        folio.setFoliosRestantes(folio.getFolioHasta() - folio.getFolioDesde() + 1);
        folio.setActivo(true);
        return folioRepository.save(folio);
    }

    public List<Folio> listar() {
        return folioRepository.findAll();
    }

    public Folio buscarPorId(Long id) {
        return folioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Folio no encontrado con id " + id));
    }

    public Folio modificar(Long id, Folio folio) {
        Folio existente = folioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Folio no encontrado con id " + id));
        existente.setSucursal(folio.getSucursal());
        existente.setTipoDocumento(folio.getTipoDocumento());
        existente.setFolioDesde(folio.getFolioDesde());
        existente.setFolioHasta(folio.getFolioHasta());
        existente.setFolioActual(folio.getFolioActual());
        existente.setFoliosRestantes(folio.getFoliosRestantes());
        existente.setActivo(folio.getActivo());
        return folioRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!folioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Folio no encontrado con id " + id);
        }
        folioRepository.deleteById(id);
    }

    // ─── CONSULTAS ───────────────────────────────────────────────────────────────

    public List<Folio> buscarPorSucursal(String sucursal) {
        return folioRepository.findBySucursal(sucursal);
    }

    public List<Folio> listarActivos() {
        return folioRepository.findByActivoTrue();
    }

    // ─── LÓGICA DE DOMINIO (del UML) ────────────────────────────────────────────

    /** Carga un rango de folios nuevo para una sucursal y tipo */
    public Folio cargar(Long id, Integer desde, Integer hasta) {
        Folio folio = folioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Folio no encontrado con id " + id));
        folio.setFolioDesde(desde);
        folio.setFolioHasta(hasta);
        folio.setFolioActual(desde);
        folio.setFoliosRestantes(hasta - desde + 1);
        folio.setActivo(true);
        return folioRepository.save(folio);
    }

    /** Obtiene el siguiente folio disponible e incrementa el contador */
    public Integer getSiguienteFolio(Long id) {
        Folio folio = folioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Folio no encontrado con id " + id));
        if (folio.getFoliosRestantes() > 0) {
            Integer siguiente = folio.getFolioActual();
            folio.setFolioActual(siguiente + 1);
            folio.setFoliosRestantes(folio.getFoliosRestantes() - 1);
            if (folio.getFoliosRestantes() == 0) {
                folio.setActivo(false);
            }
            folioRepository.save(folio);
            return siguiente;
        }
        throw new BusinessRuleException("El rango de folios " + id + " no tiene folios disponibles");
    }

    /** Verifica si el stock de folios está bajo (menos del 20% restante) */
    public boolean stockBajo(Long id) {
        Folio folio = folioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Folio no encontrado con id " + id));
        int capacidadTotal = folio.getFolioHasta() - folio.getFolioDesde() + 1;
        return folio.getFoliosRestantes() < (capacidadTotal * 0.20);
    }
}
