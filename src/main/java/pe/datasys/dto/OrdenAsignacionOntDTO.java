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
public class OrdenAsignacionOntDTO {
    @EqualsAndHashCode.Include
    private Long idOrdenAsignacionOnt;

    @JsonBackReference
    private OrdenAsignacionDTO ordenAsignacion;

    private OntDTO ont;

}
