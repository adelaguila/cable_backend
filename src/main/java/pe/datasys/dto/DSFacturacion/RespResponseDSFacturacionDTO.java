package pe.datasys.dto.DSFacturacion;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RespResponseDSFacturacionDTO {
    private String code;
    private String description;
    private List<String> notes;
}
