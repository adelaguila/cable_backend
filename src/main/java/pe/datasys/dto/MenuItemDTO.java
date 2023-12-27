package pe.datasys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MenuItemDTO {

    @EqualsAndHashCode.Include
    private Integer idMenu;
    private String icon;
    private String label;
    private String routerLink;
    private List<RoleDTO> roles;
}
