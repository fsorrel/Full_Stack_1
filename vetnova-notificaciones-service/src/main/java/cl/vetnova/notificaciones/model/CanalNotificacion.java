package cl.vetnova.notificaciones.model;

import java.util.Map;

import jakarta.persistence.*;

@Entity
@Table(name = "canal_notificacion")
public class CanalNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Boolean activo;

    @ElementCollection
    @CollectionTable(name = "canal_configuracion", joinColumns = @JoinColumn(name = "canal_id"))
    @MapKeyColumn(name = "clave")
    @Column(name = "valor")
    private Map<String, String> configuracion;

    @Column(nullable = false)
    private String sucursal;

    public CanalNotificacion() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public Map<String, String> getConfiguracion() { return configuracion; }
    public void setConfiguracion(Map<String, String> configuracion) { this.configuracion = configuracion; }

    public String getSucursal() { return sucursal; }
    public void setSucursal(String sucursal) { this.sucursal = sucursal; }
}
