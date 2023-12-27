package pe.datasys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ImprimirOrdenDTO {
    @EqualsAndHashCode.Include
    private Long idOrden;
    
    private Long idAbonado;

    private String nombreTercero;

    private String detalle;

    private String direccion;

    private String referencia;

    private String reporte;

}
