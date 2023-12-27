package pe.datasys.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "tipos_ordenes")
public class TipoOrdenEntity {
    @Id
    @EqualsAndHashCode.Include
    private Integer idTipoOrden;

    @Column(length = 150, nullable = false)
    private String nombreTipoOrden;

    @Column(length = 50)
    private String usoEstadoAbonado;
}
