package cl.vetnova.soporte.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "escalamientos_ticket")
public class EscalamientoTicket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false, unique = true)
    private Ticket ticket;
    @Column(nullable = false)
    private Long administradorId;
    @Column(nullable = false, length = 400)
    private String motivo;
    @Column(length = 800)
    private String resolucion;
    @Column(nullable = false)
    private LocalDateTime fechaEscalamiento = LocalDateTime.now();
    private LocalDateTime fechaResolucion;
    @Column(nullable = false, length = 30)
    private String estado = "PENDIENTE";
    public EscalamientoTicket() {}
    public EscalamientoTicket(Ticket ticket, Long administradorId, String motivo) { this.ticket = ticket; this.administradorId = administradorId; this.motivo = motivo; this.fechaEscalamiento = LocalDateTime.now(); this.estado = "PENDIENTE"; }
    public void cerrar(String resolucion) { this.resolucion = resolucion; this.fechaResolucion = LocalDateTime.now(); this.estado = "RESUELTO"; }
    public Long getId() { return id; }
    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }
    public Long getAdministradorId() { return administradorId; }
    public void setAdministradorId(Long administradorId) { this.administradorId = administradorId; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getResolucion() { return resolucion; }
    public void setResolucion(String resolucion) { this.resolucion = resolucion; }
    public LocalDateTime getFechaEscalamiento() { return fechaEscalamiento; }
    public void setFechaEscalamiento(LocalDateTime fechaEscalamiento) { this.fechaEscalamiento = fechaEscalamiento; }
    public LocalDateTime getFechaResolucion() { return fechaResolucion; }
    public void setFechaResolucion(LocalDateTime fechaResolucion) { this.fechaResolucion = fechaResolucion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
