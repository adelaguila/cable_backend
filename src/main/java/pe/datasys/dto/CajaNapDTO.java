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
public class CajaNapDTO {
    @EqualsAndHashCode.Include
    private Integer idCajaNap;
    
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100, message = "{nombreCajaNap.size}")
    private String nombreCajaNap;

    private String ubicacion;

    private Integer puertos;

    private String estado;
}
