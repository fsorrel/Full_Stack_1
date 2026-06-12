package cl.vetnova.inventario.service;

import cl.vetnova.inventario.client.NotificacionesClient;
import cl.vetnova.inventario.dto.MovimientoStockRequest;
import cl.vetnova.inventario.dto.MovimientoStockResponse;
import cl.vetnova.inventario.dto.StockDisponibleResponse;
import cl.vetnova.inventario.exception.BusinessRuleException;
import cl.vetnova.inventario.exception.ResourceNotFoundException;
import cl.vetnova.inventario.model.MovimientoStock;
import cl.vetnova.inventario.model.Producto;
import cl.vetnova.inventario.model.StockSucursal;
import cl.vetnova.inventario.model.TipoMovimiento;
import cl.vetnova.inventario.repository.MovimientoStockRepository;
import cl.vetnova.inventario.repository.ProductoRepository;
import cl.vetnova.inventario.repository.StockSucursalRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventarioService {
    private static final Logger log = LoggerFactory.getLogger(InventarioService.class);

    private final ProductoRepository productoRepository;
    private final StockSucursalRepository stockSucursalRepository;
    private final MovimientoStockRepository movimientoStockRepository;
    private final NotificacionesClient notificacionesClient;

    public InventarioService(ProductoRepository productoRepository,
                             StockSucursalRepository stockSucursalRepository,
                             MovimientoStockRepository movimientoStockRepository,
                             NotificacionesClient notificacionesClient) {
        this.productoRepository = productoRepository;
        this.stockSucursalRepository = stockSucursalRepository;
        this.movimientoStockRepository = movimientoStockRepository;
        this.notificacionesClient = notificacionesClient;
    }

    @Transactional(readOnly = true)
    public StockDisponibleResponse consultarStock(Long idProducto, Long idSucursal) {
        productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id " + idProducto));

        Integer disponible;
        if (idSucursal != null) {
            disponible = stockSucursalRepository.findByProductoIdAndIdSucursal(idProducto, idSucursal)
                    .map(StockSucursal::getCantidad)
                    .orElse(0);
        } else {
            disponible = stockSucursalRepository.findByProductoId(idProducto).stream()
                    .mapToInt(StockSucursal::getCantidad)
                    .sum();
        }
        log.info("event=consulta_stock productoId={} sucursalId={} disponible={}", idProducto, idSucursal, disponible);
        return new StockDisponibleResponse(idProducto, idSucursal, disponible);
    }

    @Transactional
    public MovimientoStockResponse registrarMovimiento(MovimientoStockRequest request) {
        log.info("event=registrar_movimiento productoId={} sucursalId={} tipo={} cantidad={}",
                request.getIdProducto(), request.getIdSucursal(), request.getTipo(), request.getCantidad());

        Producto producto = productoRepository.findById(request.getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id " + request.getIdProducto()));

        TipoMovimiento tipo = TipoMovimiento.valueOf(request.getTipo());

        StockSucursal stock = stockSucursalRepository
                .findByProductoIdAndIdSucursal(producto.getId(), request.getIdSucursal())
                .orElseGet(() -> {
                    StockSucursal nuevo = new StockSucursal();
                    nuevo.setProducto(producto);
                    nuevo.setIdSucursal(request.getIdSucursal());
                    nuevo.setCantidad(0);
                    return nuevo;
                });

        if (tipo == TipoMovimiento.SALIDA) {
            if (stock.getCantidad() < request.getCantidad()) {
                throw new BusinessRuleException("Stock insuficiente para el producto " + producto.getId()
                        + " en la sucursal " + request.getIdSucursal()
                        + ". Disponible: " + stock.getCantidad());
            }
            stock.setCantidad(stock.getCantidad() - request.getCantidad());
        } else {
            stock.setCantidad(stock.getCantidad() + request.getCantidad());
        }
        stockSucursalRepository.save(stock);

        MovimientoStock movimiento = new MovimientoStock();
        movimiento.setProducto(producto);
        movimiento.setIdSucursal(request.getIdSucursal());
        movimiento.setTipo(tipo);
        movimiento.setCantidad(request.getCantidad());
        movimiento.setMotivo(request.getMotivo());
        MovimientoStock guardado = movimientoStockRepository.save(movimiento);

        log.info("event=movimiento_registrado movimientoId={} stockResultante={}", guardado.getId(), stock.getCantidad());

        if (tipo == TipoMovimiento.SALIDA && stock.getCantidad() <= stock.getStockMinimo()) {
            log.warn("event=stock_critico productoId={} sucursalId={} cantidad={}",
                    producto.getId(), stock.getIdSucursal(), stock.getCantidad());
            notificacionesClient.alertarStockCritico(producto.getId(), stock.getIdSucursal(), stock.getCantidad());
        }

        return toResponse(guardado, stock.getCantidad());
    }

    @Transactional(readOnly = true)
    public List<MovimientoStockResponse> listarMovimientos(Long idProducto) {
        List<MovimientoStock> movimientos = (idProducto != null)
                ? movimientoStockRepository.findByProductoIdOrderByFechaDesc(idProducto)
                : movimientoStockRepository.findAll();
        return movimientos.stream().map(m -> toResponse(m, null)).toList();
    }

    @Transactional(readOnly = true)
    public List<StockDisponibleResponse> listarStockCritico() {
        return stockSucursalRepository.findStockCritico().stream()
                .map(s -> new StockDisponibleResponse(s.getProducto().getId(), s.getIdSucursal(), s.getCantidad()))
                .toList();
    }

    private MovimientoStockResponse toResponse(MovimientoStock movimiento, Integer stockResultante) {
        MovimientoStockResponse response = new MovimientoStockResponse();
        response.setId(movimiento.getId());
        response.setIdProducto(movimiento.getProducto().getId());
        response.setIdSucursal(movimiento.getIdSucursal());
        response.setTipo(movimiento.getTipo().name());
        response.setCantidad(movimiento.getCantidad());
        response.setMotivo(movimiento.getMotivo());
        response.setFecha(movimiento.getFecha());
        response.setStockResultante(stockResultante);
        return response;
    }
}
