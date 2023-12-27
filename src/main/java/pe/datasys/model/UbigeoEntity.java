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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ubigeos")
public class UbigeoEntity {
    @Id
    @EqualsAndHashCode.Include
    @Column(length = 6, nullable = false)
    private String codigo;
    
    @Column(length = 150, nullable = false)
    private String departamento;
    
    @Column(length = 150, nullable = false)
    private String provincia;
    
    @Column(length = 150, nullable = false)
    private String distrito;
    
    @Column(length = 150, nullable = false)
    private String capital;
}
