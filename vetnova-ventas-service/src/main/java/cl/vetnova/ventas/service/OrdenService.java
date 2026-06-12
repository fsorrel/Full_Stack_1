package cl.vetnova.ventas.service;

import cl.vetnova.ventas.client.InventarioClient;
import cl.vetnova.ventas.dto.*;
import cl.vetnova.ventas.exception.BusinessRuleException;
import cl.vetnova.ventas.exception.ResourceNotFoundException;
import cl.vetnova.ventas.model.DetalleOrden;
import cl.vetnova.ventas.model.EstadoOrden;
import cl.vetnova.ventas.model.Orden;
import cl.vetnova.ventas.repository.OrdenRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdenService {
    private static final Logger log = LoggerFactory.getLogger(OrdenService.class);

    private final OrdenRepository ordenRepository;
    private final InventarioClient inventarioClient;
    private final double iva;

    public OrdenService(OrdenRepository ordenRepository,
                        InventarioClient inventarioClient,
                        @Value("${app.iva}") double iva) {
        this.ordenRepository = ordenRepository;
        this.inventarioClient = inventarioClient;
        this.iva = iva;
    }

    @Transactional
    public OrdenResponse crearOrden(CrearOrdenRequest request) {
        log.info("event=crear_orden clienteId={} sucursalId={} items={}",
                request.getClienteId(), request.getIdSucursal(), request.getDetalles().size());

        // Regla de negocio: antes de crear la orden se valida stock contra Inventario
        for (DetalleOrdenRequest detalle : request.getDetalles()) {
            Integer disponible = inventarioClient.consultarStock(detalle.getProductoId(), request.getIdSucursal());
            if (disponible < detalle.getCantidad()) {
                throw new BusinessRuleException("Stock insuficiente para el producto " + detalle.getProductoId()
                        + ". Disponible: " + disponible + ", solicitado: " + detalle.getCantidad());
            }
        }

        Orden orden = new Orden();
        orden.setClienteId(request.getClienteId());
        orden.setIdSucursal(request.getIdSucursal());

        double subtotal = 0.0;
        for (DetalleOrdenRequest d : request.getDetalles()) {
            DetalleOrden detalle = new DetalleOrden();
            detalle.setProductoId(d.getProductoId());
            detalle.setNombreProducto(d.getNombreProducto());
            detalle.setCantidad(d.getCantidad());
            detalle.setPrecioUnitario(d.getPrecioUnitario());
            detalle.setSubtotal(d.getPrecioUnitario() * d.getCantidad());
            subtotal += detalle.getSubtotal();
            orden.addDetalle(detalle);
        }

        orden.setSubtotal(subtotal);
        orden.setImpuestos(Math.round(subtotal * iva * 100.0) / 100.0);
        orden.setTotal(orden.getSubtotal() + orden.getImpuestos());

        Orden guardada = ordenRepository.save(orden);
        log.info("event=orden_creada ordenId={} total={}", guardada.getId(), guardada.getTotal());
        return toResponse(guardada);
    }

    @Transactional(readOnly = true)
    public List<OrdenResponse> listar() {
        return ordenRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public OrdenResponse obtenerPorId(Long id) {
        return toResponse(buscarOrden(id));
    }

    @Transactional(readOnly = true)
    public boolean existe(Long id) {
        return ordenRepository.existsById(id);
    }

    @Transactional
    public OrdenResponse cambiarEstado(Long id, CambiarEstadoRequest request) {
        Orden orden = buscarOrden(id);
        EstadoOrden nuevoEstado = EstadoOrden.valueOf(request.getEstado());
        log.info("event=cambiar_estado_orden ordenId={} de={} a={}", id, orden.getEstado(), nuevoEstado);

        validarTransicion(orden.getEstado(), nuevoEstado);
        orden.setEstado(nuevoEstado);
        return toResponse(ordenRepository.save(orden));
    }

    // Reglas de transición de estado de la orden
    private void validarTransicion(EstadoOrden actual, EstadoOrden nuevo) {
        if (actual == EstadoOrden.ENTREGADA || actual == EstadoOrden.CANCELADA) {
            throw new BusinessRuleException("La orden ya está en estado final " + actual + " y no se puede modificar");
        }
        if (nuevo == EstadoOrden.ENVIADA && actual != EstadoOrden.CONFIRMADA) {
            throw new BusinessRuleException("Solo una orden CONFIRMADA puede pasar a ENVIADA");
        }
        if (nuevo == EstadoOrden.ENTREGADA && actual != EstadoOrden.ENVIADA) {
            throw new BusinessRuleException("Solo una orden ENVIADA puede pasar a ENTREGADA");
        }
        if (nuevo == EstadoOrden.CONFIRMADA && actual != EstadoOrden.PENDIENTE) {
            throw new BusinessRuleException("Solo una orden PENDIENTE puede pasar a CONFIRMADA");
        }
    }

    Orden buscarOrden(Long id) {
        return ordenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con id " + id));
    }

    OrdenResponse toResponse(Orden orden) {
        OrdenResponse response = new OrdenResponse();
        response.setId(orden.getId());
        response.setClienteId(orden.getClienteId());
        response.setIdSucursal(orden.getIdSucursal());
        response.setEstado(orden.getEstado().name());
        response.setSubtotal(orden.getSubtotal());
        response.setImpuestos(orden.getImpuestos());
        response.setTotal(orden.getTotal());
        response.setFechaCreacion(orden.getFechaCreacion());
        response.setFechaConfirmacion(orden.getFechaConfirmacion());
        response.setDetalles(orden.getDetalles().stream().map(d -> {
            DetalleOrdenResponse dr = new DetalleOrdenResponse();
            dr.setId(d.getId());
            dr.setProductoId(d.getProductoId());
            dr.setNombreProducto(d.getNombreProducto());
            dr.setCantidad(d.getCantidad());
            dr.setPrecioUnitario(d.getPrecioUnitario());
            dr.setSubtotal(d.getSubtotal());
            return dr;
        }).toList());
        response.setPagos(orden.getPagos().stream().map(p -> {
            PagoResponse pr = new PagoResponse();
            pr.setId(p.getId());
            pr.setOrdenId(orden.getId());
            pr.setMetodo(p.getMetodo());
            pr.setMonto(p.getMonto());
            pr.setEstado(p.getEstado());
            pr.setReferencia(p.getReferencia());
            pr.setFecha(p.getFecha());
            return pr;
        }).toList());
        return response;
    }
}
