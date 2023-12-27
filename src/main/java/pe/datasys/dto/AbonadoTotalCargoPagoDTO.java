package pe.datasys.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AbonadoTotalCargoPagoDTO {
    @EqualsAndHashCode.Include
    private Long idAbonado;

    private BigDecimal totalCargos;

    private BigDecimal totalPagos;
   
}
