package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.CajaNapDTO;
import pe.datasys.model.CajaNapEntity;

public interface ICajaNapService extends ICRUD<CajaNapEntity, Integer> {
    Page<CajaNapDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);
}
