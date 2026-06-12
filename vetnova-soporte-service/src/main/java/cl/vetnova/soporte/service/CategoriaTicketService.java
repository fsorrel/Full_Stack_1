package cl.vetnova.soporte.service;

import cl.vetnova.soporte.dto.CategoriaTicketRequest;
import cl.vetnova.soporte.dto.CategoriaTicketResponse;
import cl.vetnova.soporte.exception.BusinessRuleException;
import cl.vetnova.soporte.exception.ResourceNotFoundException;
import cl.vetnova.soporte.model.CategoriaTicket;
import cl.vetnova.soporte.repository.CategoriaTicketRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaTicketService {
    private static final Logger log = LoggerFactory.getLogger(CategoriaTicketService.class);
    private final CategoriaTicketRepository repository;
    public CategoriaTicketService(CategoriaTicketRepository repository) { this.repository = repository; }
    public List<CategoriaTicketResponse> listar() { return repository.findAll().stream().map(CategoriaTicketResponse::from).toList(); }
    public CategoriaTicket buscarEntidad(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada: " + id)); }
    public CategoriaTicketResponse buscar(Long id) { return CategoriaTicketResponse.from(buscarEntidad(id)); }
    @Transactional
    public CategoriaTicketResponse crear(CategoriaTicketRequest request) {
        if (repository.existsByNombreIgnoreCase(request.nombre())) throw new BusinessRuleException("Ya existe una categoría con ese nombre");
        CategoriaTicket c = new CategoriaTicket(request.nombre(), request.descripcion(), request.areaPorDefecto(), request.prioridadDefault());
        CategoriaTicket guardada = repository.save(c);
        log.info("event=categoria_ticket_created categoriaId={}", guardada.getId());
        return CategoriaTicketResponse.from(guardada);
    }
    @Transactional
    public CategoriaTicketResponse actualizar(Long id, CategoriaTicketRequest request) {
        CategoriaTicket c = buscarEntidad(id);
        c.setNombre(request.nombre()); c.setDescripcion(request.descripcion()); c.setAreaPorDefecto(request.areaPorDefecto()); c.setPrioridadDefault(request.prioridadDefault());
        log.info("event=categoria_ticket_updated categoriaId={}", id);
        return CategoriaTicketResponse.from(repository.save(c));
    }
    @Transactional
    public void desactivar(Long id) { CategoriaTicket c = buscarEntidad(id); c.setActivo(false); repository.save(c); log.info("event=categoria_ticket_disabled categoriaId={}", id); }
}
