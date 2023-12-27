package pe.datasys.dto.DSFacturacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RespLinksDSFacturacionDTO {
    private String xml;
    private String pdf;
    private String cdr;
}
