package pe.datasys.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "ordenes_asignaciones")
public class OrdenAsignacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idOrdenAsignacion;

    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    private OrdenEntity orden;
    
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
    
    private LocalDate fechaAsignacion;

    private LocalDate fechaAtencion;
    
    private String reporte;

    private Integer activo;

    @OneToMany(mappedBy = "ordenAsignacion", cascade = CascadeType.ALL)
    private List<OrdenAsignacionProductoEntity> productosUtilizados;

    @OneToMany(mappedBy = "ordenAsignacion", cascade = CascadeType.ALL)
    private List<OrdenAsignacionOntEntity> onts;
    
}
