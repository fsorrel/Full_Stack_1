package cl.vetnova.laboratorio.model;

import jakarta.persistence.CascadeType;
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
@Table(name = "muestras")
public class Muestra {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_examen_id", nullable = false, unique = true)
    private OrdenExamen ordenExamen;
    @Column(nullable = false, length = 80)
    private String tipo;
    @Column(nullable = false, length = 500)
    private String descripcion;
    @Column(nullable = false, unique = true, length = 80)
    private String codigoMuestra;
    @Column(nullable = false)
    private LocalDateTime fechaRecepcion;
    @Column(nullable = false, length = 40)
    private String estadoProcesamiento;
    @Column(nullable = false, length = 100)
    private String responsableRecepcion;
    @OneToOne(mappedBy = "muestra", cascade = CascadeType.ALL, orphanRemoval = true)
    private Procesamiento procesamiento;
    public Muestra() {}
    public Muestra(OrdenExamen ordenExamen, String tipo, String descripcion, String codigoMuestra, String responsableRecepcion) { this.ordenExamen = ordenExamen; this.tipo = tipo; this.descripcion = descripcion; this.codigoMuestra = codigoMuestra; this.responsableRecepcion = responsableRecepcion; this.fechaRecepcion = LocalDateTime.now(); this.estadoProcesamiento = "RECIBIDA"; }
    public Long getId() { return id; }
    public OrdenExamen getOrdenExamen() { return ordenExamen; }
    public void setOrdenExamen(OrdenExamen ordenExamen) { this.ordenExamen = ordenExamen; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getCodigoMuestra() { return codigoMuestra; }
    public void setCodigoMuestra(String codigoMuestra) { this.codigoMuestra = codigoMuestra; }
    public LocalDateTime getFechaRecepcion() { return fechaRecepcion; }
    public void setFechaRecepcion(LocalDateTime fechaRecepcion) { this.fechaRecepcion = fechaRecepcion; }
    public String getEstadoProcesamiento() { return estadoProcesamiento; }
    public void setEstadoProcesamiento(String estadoProcesamiento) { this.estadoProcesamiento = estadoProcesamiento; }
    public String getResponsableRecepcion() { return responsableRecepcion; }
    public void setResponsableRecepcion(String responsableRecepcion) { this.responsableRecepcion = responsableRecepcion; }
    public Procesamiento getProcesamiento() { return procesamiento; }
    public void setProcesamiento(Procesamiento procesamiento) { this.procesamiento = procesamiento; }
}
