package cl.vetnova.soporte.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "derivaciones_ticket")
public class DerivacionTicket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;
    private Long responsableAnterior;
    @Column(nullable = false)
    private Long responsableNuevo;
    @Column(nullable = false, length = 300)
    private String motivo;
    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();
    public DerivacionTicket() {}
    public DerivacionTicket(Ticket ticket, Long responsableAnterior, Long responsableNuevo, String motivo) { this.ticket = ticket; this.responsableAnterior = responsableAnterior; this.responsableNuevo = responsableNuevo; this.motivo = motivo; this.fecha = LocalDateTime.now(); }
    public Long getId() { return id; }
    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }
    public Long getResponsableAnterior() { return responsableAnterior; }
    public void setResponsableAnterior(Long responsableAnterior) { this.responsableAnterior = responsableAnterior; }
    public Long getResponsableNuevo() { return responsableNuevo; }
    public void setResponsableNuevo(Long responsableNuevo) { this.responsableNuevo = responsableNuevo; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
