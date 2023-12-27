package pe.datasys.service;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.MenuDTO;
import pe.datasys.model.Menu;

import java.util.List;

import org.springframework.data.domain.Page;

public interface IMenuService extends ICRUD<Menu, Integer> {

    List<Menu> getMenusByUsername(String username);

     Page<MenuDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);

}
