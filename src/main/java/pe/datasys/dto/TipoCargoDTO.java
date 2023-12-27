package pe.datasys.dto;

import java.math.BigDecimal;

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
public class TipoCargoDTO {
    @EqualsAndHashCode.Include
    private Integer idTipoCargo;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    private String nombreTipoCargo;

    private BigDecimal precio;
}
