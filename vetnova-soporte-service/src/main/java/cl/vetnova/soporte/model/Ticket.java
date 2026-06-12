package cl.vetnova.soporte.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long clienteId;
    @Column(nullable = false, length = 120)
    private String motivo;
    @Column(nullable = false, length = 1200)
    private String descripcion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaTicket categoria;
    @Column(nullable = false, length = 20)
    private String prioridad;
    @Column(nullable = false, length = 30)
    private String estado;
    private Long responsableId;
    @Column(nullable = false, length = 60)
    private String sucursal;
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaCierre;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DerivacionTicket> derivaciones = new ArrayList<>();

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespuestaTicket> respuestas = new ArrayList<>();

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private EscalamientoTicket escalamiento;

    public Ticket() {}

    public Ticket(Long clienteId, String motivo, String descripcion, CategoriaTicket categoria, String prioridad, String sucursal) {
        this.clienteId = clienteId; this.motivo = motivo; this.descripcion = descripcion; this.categoria = categoria;
        this.prioridad = prioridad; this.sucursal = sucursal; this.estado = "ABIERTO"; this.fechaCreacion = LocalDateTime.now();
    }

    public void cerrar() { this.estado = "CERRADO"; this.fechaCierre = LocalDateTime.now(); }
    public void escalar() { this.estado = "ESCALADO"; }
    public void derivar(Long responsableId) { this.responsableId = responsableId; this.estado = "DERIVADO"; }

    public Long getId() { return id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public CategoriaTicket getCategoria() { return categoria; }
    public void setCategoria(CategoriaTicket categoria) { this.categoria = categoria; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Long getResponsableId() { return responsableId; }
    public void setResponsableId(Long responsableId) { this.responsableId = responsableId; }
    public String getSucursal() { return sucursal; }
    public void setSucursal(String sucursal) { this.sucursal = sucursal; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDateTime fechaCierre) { this.fechaCierre = fechaCierre; }
    public List<DerivacionTicket> getDerivaciones() { return derivaciones; }
    public List<RespuestaTicket> getRespuestas() { return respuestas; }
    public EscalamientoTicket getEscalamiento() { return escalamiento; }
    public void setEscalamiento(EscalamientoTicket escalamiento) { this.escalamiento = escalamiento; }
}
