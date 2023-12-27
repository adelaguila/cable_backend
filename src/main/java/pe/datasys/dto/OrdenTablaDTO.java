package pe.datasys.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrdenTablaDTO {
    @EqualsAndHashCode.Include
    private Long idOrden;
    private LocalDate fechaRegistro;
    private String nombreTercero;
    private String nombreTipoOrden;
    private String detalle;
    private String nombreSector;
    private String direccion;
    private String estado;       
}
