package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.ViaDTO;
import pe.datasys.model.ViaEntity;

public interface IViaService extends ICRUD<ViaEntity, Integer> {
    Page<ViaDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);
}
