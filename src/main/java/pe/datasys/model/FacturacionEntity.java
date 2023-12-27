package pe.datasys.model;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "facturacion")
public class FacturacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idFacturacion;

    @ManyToOne
    @JoinColumn(name = "id_tercero", nullable = false)
    private TerceroEntity tercero;

    private LocalDate fecha;

    @Column(nullable = false, length = 2)
    private String tipoDocumento;

    @Column(nullable = false, length = 4)
    private String serie;

    @Column(nullable = false)
    private Integer numero;

    private BigDecimal pigv;

    private BigDecimal subtotal;

    private BigDecimal igv;

    private BigDecimal total;

    private LocalDate fechaPago;

    private Integer directo;

    private String referencia;

    @Column(nullable = true)
    private String linkXml;    

    @Column(nullable = true)
    private String linkPdf;    

    @Column(nullable = true)
    private String linkCdr;
    
    @Column(nullable = true)
    private String externalId;    

    @Column(nullable = true)
    private String documentoAfectado;    

    @Column(nullable = true, length = 2)
    private String notaCodigo;    

    @Column(nullable = true)
    private String notaMotivo;
    
    @Column(nullable = true)
    private String hash;    

    @Column(nullable = true)
    private Long idPago;    

    @Column(nullable = true)
    private String tipoPago;    

    private LocalDate fechaVencimiento;

    private String tipoMoneda; 
    
    private String respuestaCdr;
    
    private Long idTerceroDireccion;

    private Long idAbonado;
    
    @Column(length = 20)
    private String estado;
    
    @OneToMany(mappedBy = "facturacion", cascade = CascadeType.ALL)
    private List<FacturacionItemEntity> items;

}

