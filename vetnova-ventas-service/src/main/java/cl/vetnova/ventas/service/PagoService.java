package cl.vetnova.ventas.service;

import cl.vetnova.ventas.client.InventarioClient;
import cl.vetnova.ventas.dto.OrdenResponse;
import cl.vetnova.ventas.dto.RegistrarPagoRequest;
import cl.vetnova.ventas.exception.BusinessRuleException;
import cl.vetnova.ventas.model.EstadoOrden;
import cl.vetnova.ventas.model.Orden;
import cl.vetnova.ventas.model.Pago;
import cl.vetnova.ventas.repository.OrdenRepository;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagoService {
    private static final Logger log = LoggerFactory.getLogger(PagoService.class);

    private final OrdenRepository ordenRepository;
    private final OrdenService ordenService;
    private final InventarioClient inventarioClient;

    public PagoService(OrdenRepository ordenRepository, OrdenService ordenService, InventarioClient inventarioClient) {
        this.ordenRepository = ordenRepository;
        this.ordenService = ordenService;
        this.inventarioClient = inventarioClient;
    }

    // Regla de negocio: el pago aprobado confirma la orden y descuenta el stock en Inventario
    @Transactional
    public OrdenResponse registrarPago(Long ordenId, RegistrarPagoRequest request) {
        log.info("event=registrar_pago ordenId={} metodo={} monto={}", ordenId, request.getMetodo(), request.getMonto());

        Orden orden = ordenService.buscarOrden(ordenId);

        if (orden.getEstado() != EstadoOrden.PENDIENTE) {
            throw new BusinessRuleException("Solo se pueden pagar órdenes en estado PENDIENTE. Estado actual: " + orden.getEstado());
        }
        if (!request.getMonto().equals(orden.getTotal())) {
            throw new BusinessRuleException("El monto del pago (" + request.getMonto()
                    + ") no coincide con el total de la orden (" + orden.getTotal() + ")");
        }

        Pago pago = new Pago();
        pago.setMetodo(request.getMetodo());
        pago.setMonto(request.getMonto());
        pago.setReferencia(request.getReferencia());
        pago.setEstado("APROBADO");
        orden.addPago(pago);

        orden.setEstado(EstadoOrden.CONFIRMADA);
        orden.setFechaConfirmacion(LocalDateTime.now());

        // Descuento de stock en Inventario por cada detalle de la orden
        orden.getDetalles().forEach(detalle ->
                inventarioClient.registrarSalida(detalle.getProductoId(), orden.getIdSucursal(),
                        detalle.getCantidad(), "Venta orden " + orden.getId()));

        Orden guardada = ordenRepository.save(orden);
        log.info("event=pago_registrado ordenId={} estado={}", guardada.getId(), guardada.getEstado());
        return ordenService.toResponse(guardada);
    }
}
