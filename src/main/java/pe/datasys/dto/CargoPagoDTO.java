package pe.datasys.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CargoPagoDTO {
    @EqualsAndHashCode.Include
    private Long idCargoPago;

    @NotNull
    private CargoDTO cargo;

    @NotNull
    private PagoDTO pago;

    private BigDecimal pagado;

}
