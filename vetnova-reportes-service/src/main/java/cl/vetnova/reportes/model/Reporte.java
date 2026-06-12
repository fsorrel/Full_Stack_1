package cl.vetnova.reportes.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El tipo de reporte es obligatorio")
    @Column(nullable = false)
    private String tipo; // "ATENCION", "VENTA", "STOCK"

    @NotBlank(message = "La sucursal es obligatoria")
    @Column(nullable = false)
    private String sucursal;

    @NotNull(message = "La fecha desde es obligatoria")
    @Column(nullable = false)
    private LocalDate desde;

    @NotNull(message = "La fecha hasta es obligatoria")
    @Column(nullable = false)
    private LocalDate hasta;

    private Long generadoPor; // ID del usuario que generó el reporte

    @Column(nullable = false)
    private LocalDate generadoEn;

    @Column(nullable = false)
    private String estado; // "PENDIENTE", "GENERADO", "EXPORTADO"

    public Reporte() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getSucursal() { return sucursal; }
    public void setSucursal(String sucursal) { this.sucursal = sucursal; }

    public LocalDate getDesde() { return desde; }
    public void setDesde(LocalDate desde) { this.desde = desde; }

    public LocalDate getHasta() { return hasta; }
    public void setHasta(LocalDate hasta) { this.hasta = hasta; }

    public Long getGeneradoPor() { return generadoPor; }
    public void setGeneradoPor(Long generadoPor) { this.generadoPor = generadoPor; }

    public LocalDate getGeneradoEn() { return generadoEn; }
    public void setGeneradoEn(LocalDate generadoEn) { this.generadoEn = generadoEn; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
