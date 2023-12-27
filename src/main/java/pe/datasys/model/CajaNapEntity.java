package pe.datasys.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "cajas_nap")
public class CajaNapEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idCajaNap;

    @Column(length = 100, nullable = false)
    private String nombreCajaNap;

    @Column(length = 200)
    private String ubicacion;

    @Column(length = 1, nullable = false)
    private Integer puertos;
    
    @Column(length = 1, nullable = false)
    private String estado;

    @PrePersist
    public void prePersisten() {
        this.estado = "A";
    }
}
