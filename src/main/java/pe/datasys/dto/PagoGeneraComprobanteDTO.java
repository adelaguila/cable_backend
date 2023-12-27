package pe.datasys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PagoGeneraComprobanteDTO {
    private ComprobanteDTO comprobante;
    private TerceroDTO tercero;
}
