package cl.vetnova.laboratorio.model;

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
@Table(name = "procesamientos")
public class Procesamiento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "muestra_id", nullable = false, unique = true)
    private Muestra muestra;
    @Column(nullable = false)
    private Long tecnicoId;
    @Column(nullable = false, length = 200)
    private String metodologia;
    @Column(nullable = false)
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    @Column(nullable = false, length = 40)
    private String estado;
    @Column(length = 700)
    private String observaciones;
    public Procesamiento() {}
    public Procesamiento(Muestra muestra, Long tecnicoId, String metodologia, String observaciones) { this.muestra = muestra; this.tecnicoId = tecnicoId; this.metodologia = metodologia; this.observaciones = observaciones; this.fechaInicio = LocalDateTime.now(); this.estado = "EN_PROCESO"; }
    public void completar(String observaciones) { this.fechaFin = LocalDateTime.now(); this.estado = "COMPLETADO"; this.observaciones = observaciones; }
    public Long getId() { return id; }
    public Muestra getMuestra() { return muestra; }
    public void setMuestra(Muestra muestra) { this.muestra = muestra; }
    public Long getTecnicoId() { return tecnicoId; }
    public void setTecnicoId(Long tecnicoId) { this.tecnicoId = tecnicoId; }
    public String getMetodologia() { return metodologia; }
    public void setMetodologia(String metodologia) { this.metodologia = metodologia; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
