package pe.datasys.model;

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
@Table(name = "terceros_direcciones")
public class TerceroDireccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_tercero_direccion")
    private Long idTerceroDireccion;

    @ManyToOne
    @JoinColumn(name = "id_tercero", nullable = false)
    private TerceroEntity tercero;

    @Column(nullable = false, length = 200)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "ubigeo", nullable = false)
    private UbigeoEntity ubigeo;

}
