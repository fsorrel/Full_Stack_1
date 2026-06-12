package cl.vetnova.notificaciones.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "plantilla_mensaje")
public class PlantillaMensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String motivo;

    @Column(nullable = false)
    private String canal;

    @Column(nullable = false)
    private String asunto;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String cuerpo;

    @ElementCollection
    @CollectionTable(name = "plantilla_variables", joinColumns = @JoinColumn(name = "plantilla_id"))
    @Column(name = "variable")
    private List<String> variablesRequeridas;

    @Column(nullable = false)
    private Boolean activa;

    public PlantillaMensaje() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = canal; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getCuerpo() { return cuerpo; }
    public void setCuerpo(String cuerpo) { this.cuerpo = cuerpo; }

    public List<String> getVariablesRequeridas() { return variablesRequeridas; }
    public void setVariablesRequeridas(List<String> variablesRequeridas) { this.variablesRequeridas = variablesRequeridas; }

    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }
}
