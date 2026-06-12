package cl.vetnova.facturacion.model;

import jakarta.persistence.*;

@Entity
@Table(name = "folios")
public class Folio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String sucursal;

    @Column(nullable = false, length = 50)
    private String tipoDocumento; // BOLETA, FACTURA, NOTA_CREDITO, etc.

    @Column(nullable = false)
    private Integer folioDesde;

    @Column(nullable = false)
    private Integer folioHasta;

    @Column(nullable = false)
    private Integer folioActual;

    @Column(nullable = false)
    private Integer foliosRestantes;

    @Column(nullable = false)
    private Boolean activo;

    public Folio() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSucursal() { return sucursal; }
    public void setSucursal(String sucursal) { this.sucursal = sucursal; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public Integer getFolioDesde() { return folioDesde; }
    public void setFolioDesde(Integer folioDesde) { this.folioDesde = folioDesde; }

    public Integer getFolioHasta() { return folioHasta; }
    public void setFolioHasta(Integer folioHasta) { this.folioHasta = folioHasta; }

    public Integer getFolioActual() { return folioActual; }
    public void setFolioActual(Integer folioActual) { this.folioActual = folioActual; }

    public Integer getFoliosRestantes() { return foliosRestantes; }
    public void setFoliosRestantes(Integer foliosRestantes) { this.foliosRestantes = foliosRestantes; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
