package pe.datasys.model;

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
@Table(name = "ordenes_asignaciones_onts")
public class OrdenAsignacionOntEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idOrdenAsignacionOnt;

    @ManyToOne
    @JoinColumn(name = "id_orden_asignacion", nullable = false)
    private OrdenAsignacionEntity ordenAsignacion;
    
    @ManyToOne
    @JoinColumn(name = "serie", nullable = false)
    private OntEntity ont;
        
}
