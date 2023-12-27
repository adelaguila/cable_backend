package pe.datasys.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ComprobanteDTO {
    @EqualsAndHashCode.Include
    @Size(min = 4, max = 4)
    private String serie;

    @NotNull
    private Integer numero;

    @Size(min = 2, max = 2)
    private String tipo;
    
}
