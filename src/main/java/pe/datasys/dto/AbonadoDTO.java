package pe.datasys.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class AbonadoDTO {
    @EqualsAndHashCode.Include
    private Long idAbonado;

    @NotNull
    private TerceroDTO tercero;

    @NotNull
    private SectorDTO sector;

    @NotNull
    private ViaDTO via;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private String numero;

    private String referencia;

    @NotNull
    private CajaNapDTO cajaNap;

    @NotNull
    private PlanDTO plan;

    private LocalDate fechaRegistro;

    private LocalDate fechaActivacion;

    private LocalDate fechaUltimaLiquidacion;

    private Float latitud;

    private Float longitud;

    private String foto;

    private String estado;
    
    private Integer vendedor;
    
    private BigDecimal deuda;

    private BigDecimal saldoFavor;
    
    // @JsonManagedReference
    // private List<OrdenDTO> ordenes;

   
}
