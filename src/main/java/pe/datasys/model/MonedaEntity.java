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
@Table(name = "monedas")
public class MonedaEntity {
    @Id
    @EqualsAndHashCode.Include
    @Column(length = 5, nullable = false, unique = true)
    private String codigo;

    @Column(length = 100, nullable = false)
    private String nombreMoneda;

    @Column(length = 5, nullable = false)
    private String simbolo;

}
