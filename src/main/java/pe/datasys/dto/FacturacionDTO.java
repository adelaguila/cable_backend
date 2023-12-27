package pe.datasys.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FacturacionDTO {
    @EqualsAndHashCode.Include
    private Long idFacturacion;

    private TerceroDTO tercero;

    private LocalDate fecha;
    private String tipoDocumento;
    private String serie;
    private Integer numero;
    private BigDecimal pigv;
    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal total;
    private LocalDate fechaPago;
    private Integer directo;
    private String referencia;
}
