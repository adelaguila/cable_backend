package pe.datasys.dto;

import jakarta.validation.constraints.NotEmpty;
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
public class MonedaDTO {
    @EqualsAndHashCode.Include
    @NotNull
    @NotEmpty
    @Size(min = 1)
    private String codigo;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String nombreMoneda;
    
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 5)
    private String simbolo;

}
