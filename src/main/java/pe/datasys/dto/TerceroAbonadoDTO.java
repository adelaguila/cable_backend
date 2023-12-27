package pe.datasys.dto;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TerceroAbonadoDTO {
    
    @NotNull
    private TerceroDTO tercero;

    @NotNull
    private AbonadoDTO abonado;    
}
