package pe.datasys.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cargos")
public class CargoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idCargo;

    @ManyToOne
    @JoinColumn(name = "id_abonado", nullable = false)
    private AbonadoEntity abonado;

    @ManyToOne
    @JoinColumn(name = "id_tipo_cargo", nullable = true)
    private TipoCargoEntity tipoCargo;

    @ManyToOne
    @JoinColumn(name = "id_liquidacion", nullable = true)
    private LiquidacionEntity liquidacion;

    private LocalDate fechaEmision;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private LocalDate fechaVencimiento;

    @Column(length = 2, nullable = false)
    private Integer anio;

    // @Column(length = 2, nullable = false)
    private String periodo;

    private Integer tipo;

    @ManyToOne
    @JoinColumn(name = "id_plan", nullable = true)
    private PlanEntity plan;

    private String glosa;

    private Integer cantidad;

    private BigDecimal precio;

    private BigDecimal total;

    private BigDecimal pagado;

    private Long idRecibo;

}
