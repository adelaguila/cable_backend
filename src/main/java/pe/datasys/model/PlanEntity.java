package pe.datasys.model;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "planes")
public class PlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idPlan;

    @Column(length = 150, nullable = false)
    private String nombrePlan;

    @Column(nullable = false)
    private BigDecimal precioDia;

    @Column(nullable = false)
    private BigDecimal precioMes;

    @Column(length = 1, nullable = false)
    private String estado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
    
    @PrePersist
    public void prePersisten() {
        this.createdAt = new Date();
        this.estado = "A";
    }

    @PreUpdate
    public void preModify() {
        this.updatedAt = new Date();
    }
}
