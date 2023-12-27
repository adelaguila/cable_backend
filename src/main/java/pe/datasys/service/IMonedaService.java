package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.MonedaDTO;
import pe.datasys.model.MonedaEntity;

public interface IMonedaService extends ICRUD<MonedaEntity, String> {
    Page<MonedaDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);
}
