package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.TipoViaDTO;
import pe.datasys.model.TipoViaEntity;

public interface ITipoViaService extends ICRUD<TipoViaEntity, String> {
    Page<TipoViaDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);
}
