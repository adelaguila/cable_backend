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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ordenes")
public class OrdenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idOrden;

    @ManyToOne
    @JoinColumn(name = "id_abonado", nullable = false)
    private AbonadoEntity abonado;

    @Column(length = 150, nullable = false)
    private String detalle;

    @Column(length = 250)
    private String reporte;

    @Column(length = 50, nullable = false)
    private String estado;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    private LocalDate fechaAsignacion;

    private LocalDate fechaAtencion;

    @ManyToOne
    @JoinColumn(name = "id_tipo_orden", nullable = false)
    private TipoOrdenEntity tipoOrden;

}
