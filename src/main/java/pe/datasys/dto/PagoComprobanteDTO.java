package pe.datasys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PagoComprobanteDTO {

    private PagoDTO pago;
    private PagoGeneraComprobanteDTO pagoGeneraComprobante;
}
