package pe.datasys.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
public class TerceroDireccionDTO {
    @EqualsAndHashCode.Include
    private Long idTerceroDireccion;

    @JsonBackReference
    private TerceroDTO tercero;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 200, message = "{direccion.size}")
    private String direccion;

    @NotNull
    private UbigeoDTO ubigeo;
}
