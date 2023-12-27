package pe.datasys.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SectorDTO {
    @EqualsAndHashCode.Include
    private Integer idSector;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 150, message = "{nombreSector.size}")
    private String nombreSector;
}
