package cl.vetnova.laboratorio.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordenes_examen")
public class OrdenExamen {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long mascotaId;
    @Column(nullable = false)
    private Long veterinarioId;
    @Column(nullable = false)
    private Long recepcionistaId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_examen_id", nullable = false)
    private TipoExamen tipoExamen;
    @Column(nullable = false, length = 700)
    private String descripcion;
    @Column(nullable = false, length = 30)
    private String estado;
    @Column(nullable = false, length = 60)
    private String sucursal;
    @Column(nullable = false)
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaProgramada;

    @OneToOne(mappedBy = "ordenExamen", cascade = CascadeType.ALL, orphanRemoval = true)
    private Muestra muestra;
    @OneToOne(mappedBy = "ordenExamen", cascade = CascadeType.ALL, orphanRemoval = true)
    private ResultadoExamen resultado;

    public OrdenExamen() {}
    public OrdenExamen(Long mascotaId, Long veterinarioId, Long recepcionistaId, TipoExamen tipoExamen, String descripcion, String sucursal) {
        this.mascotaId = mascotaId; this.veterinarioId = veterinarioId; this.recepcionistaId = recepcionistaId; this.tipoExamen = tipoExamen;
        this.descripcion = descripcion; this.sucursal = sucursal; this.estado = "SOLICITADA"; this.fechaSolicitud = LocalDateTime.now();
    }
    public void programar(LocalDateTime fecha) { this.fechaProgramada = fecha; this.estado = "PROGRAMADA"; }
    public void cancelar() { this.estado = "CANCELADA"; }
    public void marcarEnProceso() { this.estado = "EN_PROCESO"; }
    public void marcarCompletada() { this.estado = "COMPLETADA"; }
    public Long getId() { return id; }
    public Long getMascotaId() { return mascotaId; }
    public void setMascotaId(Long mascotaId) { this.mascotaId = mascotaId; }
    public Long getVeterinarioId() { return veterinarioId; }
    public void setVeterinarioId(Long veterinarioId) { this.veterinarioId = veterinarioId; }
    public Long getRecepcionistaId() { return recepcionistaId; }
    public void setRecepcionistaId(Long recepcionistaId) { this.recepcionistaId = recepcionistaId; }
    public TipoExamen getTipoExamen() { return tipoExamen; }
    public void setTipoExamen(TipoExamen tipoExamen) { this.tipoExamen = tipoExamen; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getSucursal() { return sucursal; }
    public void setSucursal(String sucursal) { this.sucursal = sucursal; }
    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }
    public LocalDateTime getFechaProgramada() { return fechaProgramada; }
    public void setFechaProgramada(LocalDateTime fechaProgramada) { this.fechaProgramada = fechaProgramada; }
    public Muestra getMuestra() { return muestra; }
    public void setMuestra(Muestra muestra) { this.muestra = muestra; }
    public ResultadoExamen getResultado() { return resultado; }
    public void setResultado(ResultadoExamen resultado) { this.resultado = resultado; }
}
