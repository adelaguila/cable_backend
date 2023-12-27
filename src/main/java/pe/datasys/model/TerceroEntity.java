package pe.datasys.model;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@Table(name = "terceros")
public class TerceroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idTercero;

    @Column(length = 15, nullable = false, unique = false)
    private String dniruc;

    @Column(length = 150, nullable = false)
    private String nombreTercero;

    @Column(length = 50, nullable = true)
    private String telefono1;
    
    @Column(length = 50, nullable = true)
    private String telefono2;
    
    @Column(length = 100, nullable = true)
    private String correoElectronico;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
    
    @OneToMany(mappedBy = "tercero", cascade = CascadeType.ALL)
    private List<TerceroDireccionEntity> direcciones;

    @PrePersist
    public void prePersisten() {
        this.createdAt = new Date();
    }

    @PreUpdate
    public void preModify() {
        this.updatedAt = new Date();
    }

}
