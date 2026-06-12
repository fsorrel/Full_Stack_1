package cl.vetnova.facturacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;

@Entity
@Table(name = "documentos_tributarios")
public class DocumentoTributario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La orden asociada es obligatoria")
    @Column(nullable = false)
    private Long ordenId;

    @NotNull(message = "El cliente es obligatorio")
    @Column(nullable = false)
    private Long clienteId;

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Column(nullable = false, length = 50)
    private String tipo; // BOLETA, FACTURA, NOTA_CREDITO, etc.

    @Column(nullable = false, length = 20)
    private String folio;

    @NotNull(message = "El neto es obligatorio")
    @Positive(message = "El neto debe ser mayor a cero")
    @Column(nullable = false)
    private Double neto;

    @Column(nullable = false)
    private Double iva;

    @Column(nullable = false)
    private Double total;

    @Column(length = 50)
    private String estadoSII; // PENDIENTE, ACEPTADO, RECHAZADO, ANULADO

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;

    @Column(length = 20)
    private String rutEmisor;

    @Column(length = 100)
    private String sucursal;

    public DocumentoTributario() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrdenId() { return ordenId; }
    public void setOrdenId(Long ordenId) { this.ordenId = ordenId; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getFolio() { return folio; }
    public void setFolio(String folio) { this.folio = folio; }

    public Double getNeto() { return neto; }
    public void setNeto(Double neto) { this.neto = neto; }

    public Double getIva() { return iva; }
    public void setIva(Double iva) { this.iva = iva; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public String getEstadoSII() { return estadoSII; }
    public void setEstadoSII(String estadoSII) { this.estadoSII = estadoSII; }

    public Date getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(Date fechaEmision) { this.fechaEmision = fechaEmision; }

    public String getRutEmisor() { return rutEmisor; }
    public void setRutEmisor(String rutEmisor) { this.rutEmisor = rutEmisor; }

    public String getSucursal() { return sucursal; }
    public void setSucursal(String sucursal) { this.sucursal = sucursal; }
}
