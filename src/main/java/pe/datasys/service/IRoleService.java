package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.RoleDTO;
import pe.datasys.model.Role;

public interface IRoleService extends ICRUD<Role, Integer> {
    Page<RoleDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);
}
