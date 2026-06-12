package cl.vetnova.facturacion.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "envios_sii")
public class EnvioSII {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long documentoId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEnvio;

    @Column(length = 20)
    private String respuestaCodigo;

    @Column(length = 500)
    private String respuestaDescripcion;

    @Column(length = 50)
    private String estado; // ENVIADO, ACEPTADO, RECHAZADO, REINTENTANDO

    @Column(nullable = false)
    private Boolean reintentado;

    public EnvioSII() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDocumentoId() { return documentoId; }
    public void setDocumentoId(Long documentoId) { this.documentoId = documentoId; }

    public Date getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(Date fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public String getRespuestaCodigo() { return respuestaCodigo; }
    public void setRespuestaCodigo(String respuestaCodigo) { this.respuestaCodigo = respuestaCodigo; }

    public String getRespuestaDescripcion() { return respuestaDescripcion; }
    public void setRespuestaDescripcion(String respuestaDescripcion) { this.respuestaDescripcion = respuestaDescripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Boolean getReintentado() { return reintentado; }
    public void setReintentado(Boolean reintentado) { this.reintentado = reintentado; }
}
