package pe.datasys.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrdenDTO {
    @EqualsAndHashCode.Include
    private Long idOrden;

    // @JsonBackReference
    @NotNull
    private AbonadoDTO abonado;

    @Size(max = 50)
    private String estado;

    @NotNull
    @NotEmpty
    @Size(max = 150)
    private String detalle;

    @Size(max = 250)
    private String reporte;

    @NotNull
    private LocalDate fechaRegistro;

    private LocalDate fechaAsignacion;
    
    private LocalDate fechaAtencion;

    private TipoOrdenDTO tipoOrden;
}
