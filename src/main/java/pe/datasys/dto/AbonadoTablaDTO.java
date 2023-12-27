package pe.datasys.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AbonadoTablaDTO {
    @EqualsAndHashCode.Include
    private Long idAbonado;
    private String dniruc;
    private String nombreTercero;
    private String nombreSector;
    private String nombreVia;
    private String numero;
    private String direccion;
    private String telefonos;
    private String nombreCajaNap;
    private String nombrePlan;
    private String estado;    
    private BigDecimal deuda;    
    private BigDecimal saldoFavor;    
}
