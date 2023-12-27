package pe.datasys.model;

import java.math.BigDecimal;

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
@Table(name = "configuraciones")
public class ConfiguracionEntity {
    @Id
    @EqualsAndHashCode.Include
    private Integer idConfiguracion;

    @Column(length = 11, nullable = false)
    private String ruc;

    @Column(nullable = false)
    private String razonSocial;

    @Column(nullable = false)
    private String nombreComercial;

    @Column(nullable = false)
    private String direccion;

    private String telefono;
    
    private String telefonoCentral;

    private String email;

    private String pse;

    private String tokenPse;

    private String urlPse;

    private String urlPseBuscar;

    private String telefonoAtencionCliente;
    
    private String telefonoReclamos;

    private String bannerAvisoCobranza;
    
    @Column(length = 6, nullable = false)
    private String ubigeo;

    private BigDecimal pigv;


}
