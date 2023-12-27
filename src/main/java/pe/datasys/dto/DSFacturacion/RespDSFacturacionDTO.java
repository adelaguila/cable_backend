package pe.datasys.dto.DSFacturacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RespDSFacturacionDTO {
    private Boolean success;
    private RespDataDSFacturacionDTO data;
    private RespLinksDSFacturacionDTO links;
    private RespResponseDSFacturacionDTO response;
}
