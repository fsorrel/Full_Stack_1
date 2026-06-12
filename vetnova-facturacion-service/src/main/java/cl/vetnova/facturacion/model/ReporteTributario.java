package cl.vetnova.facturacion.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "reportes_tributarios")
public class ReporteTributario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String sucursal;

    @Column(nullable = false, length = 20)
    private String periodo; // Ej: "2024-01", "2024-02"

    @Column(nullable = false)
    private Integer totalDocumentos;

    @Column(nullable = false)
    private Double montoNeto;

    @Column(nullable = false)
    private Double montoIva;

    @Column(nullable = false)
    private Double montoTotal;

    @Temporal(TemporalType.TIMESTAMP)
    private Date generadoEn;

    public ReporteTributario() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSucursal() { return sucursal; }
    public void setSucursal(String sucursal) { this.sucursal = sucursal; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public Integer getTotalDocumentos() { return totalDocumentos; }
    public void setTotalDocumentos(Integer totalDocumentos) { this.totalDocumentos = totalDocumentos; }

    public Double getMontoNeto() { return montoNeto; }
    public void setMontoNeto(Double montoNeto) { this.montoNeto = montoNeto; }

    public Double getMontoIva() { return montoIva; }
    public void setMontoIva(Double montoIva) { this.montoIva = montoIva; }

    public Double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(Double montoTotal) { this.montoTotal = montoTotal; }

    public Date getGeneradoEn() { return generadoEn; }
    public void setGeneradoEn(Date generadoEn) { this.generadoEn = generadoEn; }
}
