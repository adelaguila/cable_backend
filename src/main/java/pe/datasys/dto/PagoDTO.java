package pe.datasys.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PagoDTO {
    @EqualsAndHashCode.Include
    private Long idPago;

    @NotNull
    private AbonadoDTO abonado;

    // private CargoDTO cargo;

    @NotNull
    private LocalDate fechaPago;

    private String referencia;

    @NotNull
    private String tipoPago;
    
    private CuentaBancariaDTO cuentaBancaria;

    @NotNull
    private BigDecimal total;

    @NotNull
    private Integer userRegistro;

    @NotNull
    private Integer userCobrador;
    
}
