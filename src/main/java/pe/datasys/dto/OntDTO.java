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
public class OntDTO {
    @EqualsAndHashCode.Include
    private String serie;

    private MarcaDTO marca;

    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String modelo;

    private String estado;
}
