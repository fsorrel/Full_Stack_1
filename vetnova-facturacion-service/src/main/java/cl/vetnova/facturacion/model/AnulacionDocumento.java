package cl.vetnova.facturacion.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "anulaciones_documento")
public class AnulacionDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long documentoId;

    @Column(nullable = false)
    private Long administradorId;

    @Column(nullable = false, length = 500)
    private String motivo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAnulacion;

    @Column(length = 50)
    private String estadoSII; // NOTIFICADO, PENDIENTE, ERROR

    public AnulacionDocumento() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDocumentoId() { return documentoId; }
    public void setDocumentoId(Long documentoId) { this.documentoId = documentoId; }

    public Long getAdministradorId() { return administradorId; }
    public void setAdministradorId(Long administradorId) { this.administradorId = administradorId; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public Date getFechaAnulacion() { return fechaAnulacion; }
    public void setFechaAnulacion(Date fechaAnulacion) { this.fechaAnulacion = fechaAnulacion; }

    public String getEstadoSII() { return estadoSII; }
    public void setEstadoSII(String estadoSII) { this.estadoSII = estadoSII; }
}
