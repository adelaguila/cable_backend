package pe.datasys.dto;

import java.util.List;

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
public class UserDTO {
    @EqualsAndHashCode.Include
    private Integer idUser;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 150)
    private String name;

    @Size(min = 3, max = 150)
    private String password;

    @NotNull
    private boolean enabled;

    private List<RoleDTO> roles;
}
