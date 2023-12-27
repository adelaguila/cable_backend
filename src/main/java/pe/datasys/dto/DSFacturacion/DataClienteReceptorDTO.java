package pe.datasys.dto.DSFacturacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DataClienteReceptorDTO {
    private String codigo_tipo_documento_identidad;
    private String numero_documento;
    private String apellidos_y_nombres_o_razon_social;
    private String codigo_pais;
    private String direccion;
    private String ubigeo;
    private String correo_electronico;
    private String telefono;
}
