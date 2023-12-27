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
@Table(name = "tipo_vias")
public class TipoViaEntity {
    @Id
    @EqualsAndHashCode.Include
    @Column(length = 5, nullable = false, unique = true)
    private String idTipoVia;

    @Column(length = 50, nullable = false)
    private String nombreTipoVia;
}
