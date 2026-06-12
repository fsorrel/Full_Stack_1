package cl.vetnova.notificaciones.model;

import java.util.List;
import java.util.Map;

import jakarta.persistence.*;

@Entity
@Table(name = "configuracion_alerta")
public class ConfiguracionAlerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Boolean activa;

    @Column(nullable = false)
    private Integer anticipacionHoras;

    @ElementCollection
    @CollectionTable(name = "canales_activos", joinColumns = @JoinColumn(name = "configuracion_id"))
    @Column(name = "canal")
    private List<String> canalesActivos;

    @Column(nullable = false)
    private String sucursal;

    public ConfiguracionAlerta() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }

    public Integer getAnticipacionHoras() { return anticipacionHoras; }
    public void setAnticipacionHoras(Integer anticipacionHoras) { this.anticipacionHoras = anticipacionHoras; }

    public List<String> getCanalesActivos() { return canalesActivos; }
    public void setCanalesActivos(List<String> canalesActivos) { this.canalesActivos = canalesActivos; }

    public String getSucursal() { return sucursal; }
    public void setSucursal(String sucursal) { this.sucursal = sucursal; }
}
