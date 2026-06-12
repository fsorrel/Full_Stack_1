package cl.vetnova.soporte.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categorias_ticket")
public class CategoriaTicket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 80)
    private String nombre;
    @Column(nullable = false, length = 250)
    private String descripcion;
    @Column(nullable = false, length = 60)
    private String areaPorDefecto;
    @Column(nullable = false)
    private Integer prioridadDefault;
    @Column(nullable = false)
    private Boolean activo = true;

    public CategoriaTicket() {}
    public CategoriaTicket(String nombre, String descripcion, String areaPorDefecto, Integer prioridadDefault) {
        this.nombre = nombre; this.descripcion = descripcion; this.areaPorDefecto = areaPorDefecto; this.prioridadDefault = prioridadDefault; this.activo = true;
    }
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getAreaPorDefecto() { return areaPorDefecto; }
    public void setAreaPorDefecto(String areaPorDefecto) { this.areaPorDefecto = areaPorDefecto; }
    public Integer getPrioridadDefault() { return prioridadDefault; }
    public void setPrioridadDefault(Integer prioridadDefault) { this.prioridadDefault = prioridadDefault; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
