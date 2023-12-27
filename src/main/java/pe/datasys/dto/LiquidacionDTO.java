package pe.datasys.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LiquidacionDTO {
    @EqualsAndHashCode.Include
    private Integer idLiquidacion;

    @NotNull
    private LocalDate fechaEmision;

    private LocalDate fechaCierre;
    
    private LocalDate fechaVencimiento;

    private LocalDate fechaCorte;

    private Integer periodo;

    private Integer anio;

    private String mes;
}
