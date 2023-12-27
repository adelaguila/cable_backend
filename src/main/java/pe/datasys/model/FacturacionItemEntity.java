package pe.datasys.model;

import java.math.BigDecimal;

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
@Table(name = "facturacion_items")
public class FacturacionItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_tercero_direccion")
    private Long idFacturacionItem;

    @ManyToOne
    @JoinColumn(name = "id_facturacion", nullable = false)
    private FacturacionEntity facturacion;

    @Column(nullable = false)
    private String glosa;

    private Integer cantidad;

    private BigDecimal precio;

    private BigDecimal subtotal;

    private BigDecimal igv;

    private BigDecimal total;

    private String referencia;
    
}
