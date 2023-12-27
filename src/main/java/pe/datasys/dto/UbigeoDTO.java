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
public class UbigeoDTO {
    @EqualsAndHashCode.Include
    @NotNull
    @NotEmpty
    @Size(min = 6, max = 6, message = "{codigoUbigeo.size}")
    private String codigo;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100, message = "{departamento.size}")
    private String departamento;
    
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100, message = "{provincia.size}")
    private String provincia;
    
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100, message = "{distrito.size}")
    private String distrito;
    
    @NotEmpty
    @Size(max = 100, message = "{capital.size}")
    private String capital;
}
