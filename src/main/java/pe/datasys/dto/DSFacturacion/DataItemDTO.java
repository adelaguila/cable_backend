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
public class DataItemDTO {
    private String codigo_interno;
    private String descripcion;
    private String codigo_producto_sunat;
    private String codigo_producto_gsl;
    private String unidad_de_medida;
    private Integer cantidad;
    private BigDecimal valor_unitario;
    private String codigo_tipo_precio;
    private BigDecimal precio_unitario;
    private String codigo_tipo_afectacion_igv;
    private BigDecimal total_base_igv;
    private BigDecimal porcentaje_igv;
    private BigDecimal total_igv;
    private BigDecimal total_impuestos;
    private BigDecimal total_valor_item;
    private BigDecimal total_item;
}
