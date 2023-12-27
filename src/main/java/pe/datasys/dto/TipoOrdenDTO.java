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
public class TipoOrdenDTO {
    @EqualsAndHashCode.Include
    private Integer idTipoOrden;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 150, message = "{nombreTipoOrden.size}")
    private String nombreTipoOrden;

    private String usoEstadoAbonado;
}
