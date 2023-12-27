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
public class TipoViaDTO {
    @EqualsAndHashCode.Include
    private String idTipoVia;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    private String nombreTipoVia;
}
