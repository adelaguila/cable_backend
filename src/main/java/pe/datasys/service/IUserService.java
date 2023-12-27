package pe.datasys.service;

import java.util.List;

// import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.UserDTO;
import pe.datasys.model.User;

public interface IUserService extends ICRUD<User, Integer> {
    Page<UserDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);

    User findOneByUsername(String username);

    void changePassword(String password, String username);

}
