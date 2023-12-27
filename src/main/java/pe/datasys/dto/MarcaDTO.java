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
public class MarcaDTO {
    @EqualsAndHashCode.Include
    private Integer idMarca;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100, message = "{nombreMarca.size}")
    private String nombreMarca;
}
