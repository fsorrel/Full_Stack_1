package cl.vetnova.soporte.service;

import cl.vetnova.soporte.client.AuthClient;
import cl.vetnova.soporte.dto.ActualizarTicketRequest;
import cl.vetnova.soporte.dto.CerrarTicketRequest;
import cl.vetnova.soporte.dto.CrearTicketRequest;
import cl.vetnova.soporte.dto.DerivarTicketRequest;
import cl.vetnova.soporte.dto.EscalarTicketRequest;
import cl.vetnova.soporte.dto.ResponderTicketRequest;
import cl.vetnova.soporte.dto.RespuestaTicketResponse;
import cl.vetnova.soporte.dto.TicketResponse;
import cl.vetnova.soporte.exception.BusinessRuleException;
import cl.vetnova.soporte.exception.ResourceNotFoundException;
import cl.vetnova.soporte.model.CategoriaTicket;
import cl.vetnova.soporte.model.DerivacionTicket;
import cl.vetnova.soporte.model.EscalamientoTicket;
import cl.vetnova.soporte.model.RespuestaTicket;
import cl.vetnova.soporte.model.Ticket;
import cl.vetnova.soporte.repository.DerivacionTicketRepository;
import cl.vetnova.soporte.repository.EscalamientoTicketRepository;
import cl.vetnova.soporte.repository.RespuestaTicketRepository;
import cl.vetnova.soporte.repository.TicketRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {
    private static final Logger log = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final RespuestaTicketRepository respuestaRepository;
    private final DerivacionTicketRepository derivacionRepository;
    private final EscalamientoTicketRepository escalamientoRepository;
    private final CategoriaTicketService categoriaService;
    private final AuthClient authClient;

    public TicketService(TicketRepository ticketRepository, RespuestaTicketRepository respuestaRepository, DerivacionTicketRepository derivacionRepository,
                         EscalamientoTicketRepository escalamientoRepository, CategoriaTicketService categoriaService, AuthClient authClient) {
        this.ticketRepository = ticketRepository; this.respuestaRepository = respuestaRepository; this.derivacionRepository = derivacionRepository;
        this.escalamientoRepository = escalamientoRepository; this.categoriaService = categoriaService; this.authClient = authClient;
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> listar(String estado) {
        List<Ticket> tickets = estado == null ? ticketRepository.findAll() : ticketRepository.findByEstadoIgnoreCase(estado);
        return tickets.stream().map(TicketResponse::from).toList();
    }
    @Transactional(readOnly = true)
    public Ticket buscarEntidad(Long id) { return ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado: " + id)); }
    @Transactional(readOnly = true)
    public TicketResponse buscar(Long id) { return TicketResponse.from(buscarEntidad(id)); }

    @Transactional
    public TicketResponse crear(CrearTicketRequest request) {
        validarUsuarioRemoto(request.clienteId(), "cliente");
        CategoriaTicket categoria = categoriaService.buscarEntidad(request.categoriaId());
        if (!Boolean.TRUE.equals(categoria.getActivo())) throw new BusinessRuleException("La categoría se encuentra desactivada");
        Ticket ticket = new Ticket(request.clienteId(), request.motivo(), request.descripcion(), categoria, request.prioridad(), request.sucursal());
        Ticket guardado = ticketRepository.save(ticket);
        log.info("event=ticket_created ticketId={} clienteId={} categoriaId={} prioridad={}", guardado.getId(), guardado.getClienteId(), categoria.getId(), guardado.getPrioridad());
        return TicketResponse.from(guardado);
    }

    @Transactional
    public TicketResponse actualizar(Long id, ActualizarTicketRequest request) {
        Ticket ticket = buscarEntidad(id);
        if ("CERRADO".equals(ticket.getEstado())) throw new BusinessRuleException("No se puede modificar un ticket cerrado");
        ticket.setMotivo(request.motivo()); ticket.setDescripcion(request.descripcion()); ticket.setPrioridad(request.prioridad()); ticket.setEstado(request.estado());
        log.info("event=ticket_updated ticketId={} estado={}", id, request.estado());
        return TicketResponse.from(ticketRepository.save(ticket));
    }

    @Transactional
    public RespuestaTicketResponse responder(Long ticketId, ResponderTicketRequest request) {
        Ticket ticket = buscarEntidad(ticketId);
        if ("CERRADO".equals(ticket.getEstado())) throw new BusinessRuleException("No se puede responder un ticket cerrado");
        validarUsuarioRemoto(request.autorId(), "autor");
        RespuestaTicket respuesta = new RespuestaTicket(ticket, request.autorId(), request.contenido(), request.visible() == null || request.visible());
        RespuestaTicket guardada = respuestaRepository.save(respuesta);
        ticket.setEstado("EN_REVISION");
        ticketRepository.save(ticket);
        log.info("event=ticket_answered ticketId={} respuestaId={} autorId={}", ticketId, guardada.getId(), request.autorId());
        return RespuestaTicketResponse.from(guardada);
    }

    @Transactional
    public TicketResponse derivar(Long ticketId, DerivarTicketRequest request) {
        Ticket ticket = buscarEntidad(ticketId);
        if ("CERRADO".equals(ticket.getEstado())) throw new BusinessRuleException("No se puede derivar un ticket cerrado");
        validarUsuarioRemoto(request.responsableNuevo(), "responsable nuevo");
        DerivacionTicket derivacion = new DerivacionTicket(ticket, ticket.getResponsableId(), request.responsableNuevo(), request.motivo());
        derivacionRepository.save(derivacion);
        ticket.derivar(request.responsableNuevo());
        ticketRepository.save(ticket);
        log.info("event=ticket_derived ticketId={} responsableNuevo={}", ticketId, request.responsableNuevo());
        return TicketResponse.from(ticket);
    }

    @Transactional
    public TicketResponse escalar(Long ticketId, EscalarTicketRequest request) {
        Ticket ticket = buscarEntidad(ticketId);
        if (ticket.getEscalamiento() != null) throw new BusinessRuleException("El ticket ya fue escalado");
        validarUsuarioRemoto(request.administradorId(), "administrador");
        EscalamientoTicket escalamiento = new EscalamientoTicket(ticket, request.administradorId(), request.motivo());
        escalamientoRepository.save(escalamiento);
        ticket.setEscalamiento(escalamiento);
        ticket.escalar();
        ticketRepository.save(ticket);
        log.info("event=ticket_escalated ticketId={} administradorId={}", ticketId, request.administradorId());
        return TicketResponse.from(ticket);
    }

    @Transactional
    public TicketResponse cerrar(Long ticketId, CerrarTicketRequest request) {
        Ticket ticket = buscarEntidad(ticketId);
        if (ticket.getRespuestas().isEmpty()) throw new BusinessRuleException("Para cerrar un ticket debe existir al menos una respuesta registrada");
        if (ticket.getEscalamiento() != null && "PENDIENTE".equals(ticket.getEscalamiento().getEstado())) {
            ticket.getEscalamiento().cerrar(request.resolucion());
        }
        ticket.cerrar();
        ticketRepository.save(ticket);
        log.info("event=ticket_closed ticketId={}", ticketId);
        return TicketResponse.from(ticket);
    }

    private void validarUsuarioRemoto(Long usuarioId, String etiqueta) {
        if (!authClient.usuarioExiste(usuarioId)) throw new BusinessRuleException("El " + etiqueta + " informado no existe en Auth Service: " + usuarioId);
    }
}
