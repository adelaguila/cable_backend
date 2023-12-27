package pe.datasys.dto.DSFacturacion;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DataCuotaDTO {
    private String fecha;
    private String codigo_tipo_moneda;
    private BigDecimal monto;
}
