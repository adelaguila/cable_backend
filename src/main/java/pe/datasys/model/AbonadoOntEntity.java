package pe.datasys.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "abonados_onts")
public class AbonadoOntEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idAbonadoOnt;

    @ManyToOne
    @JoinColumn(name = "id_abonado", nullable = false)
    private AbonadoEntity abonado;

    @ManyToOne
    @JoinColumn(name = "serie", nullable = false)
    private OntEntity ont;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    
    @Column(length = 100, nullable = false)
    private String proceso;
}
