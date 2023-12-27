package pe.datasys.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CargoDTO {
    @EqualsAndHashCode.Include
    private Long idCargo;

    @NotNull
    private AbonadoDTO abonado;

    @NotNull
    private TipoCargoDTO tipoCargo;

    private LiquidacionDTO liquidacion;

    @NotNull
    private LocalDate fechaEmision;

    private LocalDate fechaInicio;
    
    private LocalDate fechaFin;

    private LocalDate fechaVencimiento;

    @NotNull
    private Integer anio;

    // @NotNull
    private String periodo;
    
    private Integer tipo;

    private PlanDTO plan;

    @NotNull
    @NotEmpty
    private String glosa;

    private Integer cantidad;
    
    private BigDecimal precio;

    private BigDecimal total;

    private BigDecimal pagado;


}
