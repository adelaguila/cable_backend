package pe.datasys.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cuentas_bancarias")
public class CuentaBancariaEntity {
    @Id
    @EqualsAndHashCode.Include
    @Column(length = 20, nullable = false)
    private String numeroCuenta;

    @ManyToOne
    @JoinColumn(name = "id_banco", nullable = false)
    private BancoEntity banco;

    @ManyToOne
    @JoinColumn(name = "moneda", nullable = false)
    private MonedaEntity moneda;

    @Column(length = 150, nullable = false)
    private String descripcion;

    @Column(length = 50)
    private String cci;

    @Column()
    private BigDecimal saldoInicial;

}
