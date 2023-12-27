package pe.datasys.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductoDTO {
    @EqualsAndHashCode.Include
    private Integer idProducto;

    @NotNull
    @NotEmpty
    private String nombreProducto;

    @NotNull
    @NotEmpty
    private String unidadMedida;

    @NotNull
    private Integer stock;
    
}
