package pe.datasys.dto.DSFacturacion;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DataComprobanteDTO {
    private String serie_documento;
    private Integer numero_documento;
    private String fecha_de_emision;
    private String hora_de_emision;
    private String codigo_tipo_operacion;
    private String codigo_tipo_documento;
    private String codigo_tipo_moneda;
    private String fecha_de_vencimiento;
    private String numero_orden_de_compra;
    private DataDocumentoAfectadoDTO documento_afectado;
    private String codigo_tipo_nota;
    private String motivo_o_sustento_de_nota;
    private DataClienteReceptorDTO datos_del_cliente_o_receptor;
    private String codigo_condicion_de_pago;
    private List<DataCuotaDTO> cuotas;
    private DataTotalesDTO totales;
    private List<DataItemDTO> items;
    
}
