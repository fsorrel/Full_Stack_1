package cl.vetnova.notificaciones.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "historial_mensaje")
public class HistorialMensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long notificacionId;

    @Column(nullable = false)
    private Long destinatarioId;

    @Column(nullable = false)
    private String canal;

    @Column(nullable = false)
    private String motivo;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(columnDefinition = "TEXT")
    private String detalle;

    public HistorialMensaje() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getNotificacionId() { return notificacionId; }
    public void setNotificacionId(Long notificacionId) { this.notificacionId = notificacionId; }

    public Long getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(Long destinatarioId) { this.destinatarioId = destinatarioId; }

    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = canal; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
}
