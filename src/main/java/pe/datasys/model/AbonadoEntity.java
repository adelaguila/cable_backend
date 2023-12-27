package pe.datasys.model;

import java.time.LocalDate;
import java.util.List;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
@Table(name = "abonados")
public class AbonadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idAbonado;

    @ManyToOne
    @JoinColumn(name = "id_tercero", nullable = false)
    private TerceroEntity tercero;

    @ManyToOne
    @JoinColumn(name = "id_sector", nullable = false)
    private SectorEntity sector;

    @ManyToOne
    @JoinColumn(name = "id_via", nullable = false)
    private ViaEntity via;

    @Column(nullable = false, length = 50)
    private String numero;

    @Column(nullable = false, length = 200)
    private String referencia;

    @ManyToOne
    @JoinColumn(name = "id_caja_nap", nullable = false)
    private CajaNapEntity cajaNap;

    @ManyToOne
    @JoinColumn(name = "id_plan", nullable = false)
    private PlanEntity plan;

    // @Temporal(TemporalType.TIMESTAMP)
    private LocalDate fechaRegistro;

    // @Temporal(TemporalType.TIMESTAMP)
    private LocalDate fechaActivacion;

    // @Temporal(TemporalType.TIMESTAMP)
    private LocalDate fechaUltimaLiquidacion;

    @Column(nullable = true)
    private Float latitud;

    @Column(nullable = true)
    private Float longitud;

    @Column(nullable = true)
    private Integer idUsuarioRegistro;

    @Column(nullable = true)
    private Integer vendedor;

    @Column(nullable = true)
    private Integer idUsuarioModificacion;    

    @Column(nullable = true)
    private String foto;    

    @Column(length = 20)
    private String estado;
    
    private BigDecimal deuda;

    private BigDecimal saldoFavor;
    
    @PrePersist
    public void prePersisten() {
        this.fechaRegistro = LocalDate.now();
        this.estado = "REGISTRADO";
    }

    @OneToMany(mappedBy = "abonado")
    private List<OrdenEntity> ordenes;

}

