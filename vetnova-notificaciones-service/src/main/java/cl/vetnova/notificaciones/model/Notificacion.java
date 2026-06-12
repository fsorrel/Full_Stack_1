package cl.vetnova.notificaciones.model;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "notificacion")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El destinatario es obligatorio")
    @Column(nullable = false)
    private Long destinatarioId;

    @NotBlank(message = "El tipo de destinatario es obligatorio")
    @Column(nullable = false)
    private String tipoDestinatario;

    @NotBlank(message = "El canal es obligatorio")
    @Column(nullable = false)
    private String canal;

    @NotBlank(message = "El motivo es obligatorio")
    @Column(nullable = false)
    private String motivo;

    @NotBlank(message = "El asunto es obligatorio")
    @Column(nullable = false)
    private String asunto;

    @NotBlank(message = "El mensaje es obligatorio")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(nullable = false)
    private String estado;

    @Column
    private Date programadaPara;

    @Column
    private Date enviadaEn;

    @Column(nullable = false)
    private Integer intentos = 0;

    public Notificacion() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(Long destinatarioId) { this.destinatarioId = destinatarioId; }

    public String getTipoDestinatario() { return tipoDestinatario; }
    public void setTipoDestinatario(String tipoDestinatario) { this.tipoDestinatario = tipoDestinatario; }

    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = canal; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getProgramadaPara() { return programadaPara; }
    public void setProgramadaPara(Date programadaPara) { this.programadaPara = programadaPara; }

    public Date getEnviadaEn() { return enviadaEn; }
    public void setEnviadaEn(Date enviadaEn) { this.enviadaEn = enviadaEn; }

    public Integer getIntentos() { return intentos; }
    public void setIntentos(Integer intentos) { this.intentos = intentos; }
}
