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
public class OrdenAsignacionTablaDTO {
    @EqualsAndHashCode.Include
    private Long idOrdenAsignacion;
    private Long idOrden;
    private LocalDate fechaRegistro;
    private LocalDate fechaAsignacion;
    private LocalDate fechaAtencion;
    private String nombreTercero;
    private String nombreTipoOrden;
    private String tecnico;
    private String nombreSector;
    private String direccion;    
    private String estado;    
    private String reporte;    
}
