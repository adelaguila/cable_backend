package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.TipoCargoDTO;
import pe.datasys.model.TipoCargoEntity;

public interface ITipoCargoService extends ICRUD<TipoCargoEntity, Integer> {
    Page<TipoCargoDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);
}
