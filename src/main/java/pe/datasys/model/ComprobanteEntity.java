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
@Table(name = "comprobantes")
public class ComprobanteEntity {
    @Id
    @EqualsAndHashCode.Include
    @Column(length = 4, nullable = false, unique = true)
    private String serie;

    @Column()
    private Integer numero;

    @Column(length = 2)
    private String tipo;
}
