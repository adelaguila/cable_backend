package pe.datasys.model;

import java.time.LocalDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pagos")
public class PagoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idPago;

    @ManyToOne
    @JoinColumn(name = "id_abonado", nullable = true)
    private AbonadoEntity abonado;

    @ManyToOne
    @JoinColumn(name = "id_cargo", nullable = true)
    private CargoEntity cargo;

    @Column(nullable = false)
    private LocalDate fechaPago;

    private String referencia;

    @Column(nullable = false)
    private String tipoPago;

    @ManyToOne
    @JoinColumn(name = "numero_cuenta", nullable = true)
    private CuentaBancariaEntity cuentaBancaria;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = true)
    private Integer userRegistro;

    @Column(nullable = true)
    private Integer userCobrador;

    private Long idRecibo;

    private Long idFacturacion;

}
