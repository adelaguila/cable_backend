package pe.datasys.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ChangePasswordDTO {
    @NotNull
    private String username;

    @NotNull
    private String passwordActual;

    @NotNull
    private String passwordNuevo;

}
