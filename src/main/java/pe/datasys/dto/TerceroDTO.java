package pe.datasys.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TerceroDTO {
    @EqualsAndHashCode.Include
    private Long idTercero;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 15, message = "{dniruc.size}")
    private String dniruc;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 150, message = "{nombreTercero.size}")
    private String nombreTercero;

    // @Pattern(regexp = "[0-9]+")
    @Size(max = 50, message = "{telefono.size}")
    private String telefono1;

    @Size(max = 50, message = "{telefono.size}")
    private String telefono2;

    @Email
    @Size(max = 100, message = "{email.size}")
    private String correoElectronico;

    @JsonManagedReference
    @NotNull
    private List<TerceroDireccionDTO> direcciones;

    public TerceroDTO(Long idTercero,
            @NotNull @NotEmpty @Size(min = 8, max = 15, message = "{dniruc.size}") String dniruc,
            @NotNull @NotEmpty @Size(min = 2, max = 150, message = "{nombreTercero.size}") String nombreTercero,
            @Pattern(regexp = "[0-9]+") @Size(max = 50, message = "{telefono.size}") String telefono1,
            @Size(max = 50, message = "{telefono.size}") String telefono2,
            @Email @Size(max = 100, message = "{email.size}") String correoElectronico) {
        this.idTercero = idTercero;
        this.dniruc = dniruc;
        this.nombreTercero = nombreTercero;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.correoElectronico = correoElectronico;
    }

    
}
