package pe.datasys.dto;

import java.math.BigDecimal;

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
public class CuentaBancariaDTO {
    @EqualsAndHashCode.Include
    @NotNull
    @NotEmpty
    private String numeroCuenta;

    private BancoDTO banco;

    private MonedaDTO moneda;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 150)
    private String descripcion;

    private String cci;

    private BigDecimal saldoInicial;
}
