package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.TerceroDTO;
import pe.datasys.model.TerceroEntity;

public interface ITerceroService extends ICRUD<TerceroEntity, Long> {
    Page<TerceroDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);

    TerceroEntity checkDniruc(String dniruc);

    List<TerceroEntity> autocomplete(String term);
}
