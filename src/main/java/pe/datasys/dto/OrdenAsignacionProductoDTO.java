package pe.datasys.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrdenAsignacionProductoDTO {
    @EqualsAndHashCode.Include
    private Long idOrdenAsignacionProducto;

    @JsonBackReference
    private OrdenAsignacionDTO ordenAsignacion;

    private ProductoDTO producto;

    private Integer cantidad;
}
