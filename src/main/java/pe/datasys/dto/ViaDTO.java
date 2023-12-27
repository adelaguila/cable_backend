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
public class ViaDTO {
    @EqualsAndHashCode.Include
    private Integer idVia;

    private TipoViaDTO tipoVia;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 150)
    private String nombreVia;

    // public ViaDTO(Integer idVia,
    //         @NotNull @NotEmpty @Size(min = 3, max = 150) String nombreVia, String nombreTipoVia) {
    //     this.idVia = idVia;
    //     this.nombreVia = nombreVia;
    // }

}
