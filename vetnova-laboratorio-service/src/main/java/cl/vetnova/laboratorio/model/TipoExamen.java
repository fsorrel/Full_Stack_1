package cl.vetnova.laboratorio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipos_examen")
public class TipoExamen {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;
    @Column(nullable = false, length = 500)
    private String descripcion;
    @Column(nullable = false)
    private Integer tiempoEstimadoHoras;
    @Column(nullable = false)
    private Boolean requiereMuestra;
    @Column(nullable = false, length = 700)
    private String instrucciones;
    @Column(nullable = false)
    private Boolean activo = true;
    public TipoExamen() {}
    public TipoExamen(String nombre, String descripcion, Integer tiempoEstimadoHoras, Boolean requiereMuestra, String instrucciones) { this.nombre = nombre; this.descripcion = descripcion; this.tiempoEstimadoHoras = tiempoEstimadoHoras; this.requiereMuestra = requiereMuestra; this.instrucciones = instrucciones; this.activo = true; }
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getTiempoEstimadoHoras() { return tiempoEstimadoHoras; }
    public void setTiempoEstimadoHoras(Integer tiempoEstimadoHoras) { this.tiempoEstimadoHoras = tiempoEstimadoHoras; }
    public Boolean getRequiereMuestra() { return requiereMuestra; }
    public void setRequiereMuestra(Boolean requiereMuestra) { this.requiereMuestra = requiereMuestra; }
    public String getInstrucciones() { return instrucciones; }
    public void setInstrucciones(String instrucciones) { this.instrucciones = instrucciones; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
