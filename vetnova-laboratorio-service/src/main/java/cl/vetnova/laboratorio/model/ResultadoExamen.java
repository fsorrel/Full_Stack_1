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
@Table(name = "resultados_examen")
public class ResultadoExamen {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_examen_id", nullable = false, unique = true)
    private OrdenExamen ordenExamen;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "muestra_id")
    private Muestra muestra;
    @Column(nullable = false)
    private Long tecnicoId;
    @Column(nullable = false, length = 2000)
    private String resultado;
    @Column(length = 1000)
    private String observaciones;
    @Column(nullable = false, length = 1000)
    private String interpretacion;
    @Column(nullable = false)
    private Boolean disponible;
    @Column(nullable = false)
    private LocalDateTime fechaRegistro;
    public ResultadoExamen() {}
    public ResultadoExamen(OrdenExamen ordenExamen, Muestra muestra, Long tecnicoId, String resultado, String observaciones, String interpretacion, Boolean disponible) { this.ordenExamen = ordenExamen; this.muestra = muestra; this.tecnicoId = tecnicoId; this.resultado = resultado; this.observaciones = observaciones; this.interpretacion = interpretacion; this.disponible = disponible; this.fechaRegistro = LocalDateTime.now(); }
    public Long getId() { return id; }
    public OrdenExamen getOrdenExamen() { return ordenExamen; }
    public void setOrdenExamen(OrdenExamen ordenExamen) { this.ordenExamen = ordenExamen; }
    public Muestra getMuestra() { return muestra; }
    public void setMuestra(Muestra muestra) { this.muestra = muestra; }
    public Long getTecnicoId() { return tecnicoId; }
    public void setTecnicoId(Long tecnicoId) { this.tecnicoId = tecnicoId; }
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public String getInterpretacion() { return interpretacion; }
    public void setInterpretacion(String interpretacion) { this.interpretacion = interpretacion; }
    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
