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
public class DataTotalesDTO {
    private BigDecimal total_exportacion;
    private BigDecimal total_operaciones_gravadas;
    private BigDecimal total_operaciones_inafectas;
    private BigDecimal total_operaciones_exoneradas;
    private BigDecimal total_operaciones_gratuitas;
    private BigDecimal total_igv;
    private BigDecimal total_impuestos;
    private BigDecimal total_valor;
    private BigDecimal total_venta;
}
