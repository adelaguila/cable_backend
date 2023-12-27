package pe.datasys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrdenAsignacionDTO {
    @EqualsAndHashCode.Include
    private Long idOrdenAsignacion;

    private OrdenDTO orden;

    private UserDTO user;

    private LocalDate fechaAsignacion;

    private LocalDate fechaAtencion;

    private String reporte;

    @JsonManagedReference
    private List<OrdenAsignacionProductoDTO> productosUtilizados;

    @JsonManagedReference
    private List<OrdenAsignacionOntDTO> onts;
    
}
