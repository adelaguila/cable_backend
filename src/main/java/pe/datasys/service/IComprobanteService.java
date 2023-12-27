package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.ComprobanteDTO;
import pe.datasys.model.ComprobanteEntity;

public interface IComprobanteService extends ICRUD<ComprobanteEntity, String> {
    Page<ComprobanteDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);
    
}
