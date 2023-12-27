package pe.datasys.dto.DSFacturacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RespDataDSFacturacionDTO {
    private String number;
    private String filename;
    private String external_id;
    private String number_to_letter;
    private String hash;
    private String qr;
}
