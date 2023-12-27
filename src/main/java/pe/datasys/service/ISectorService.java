package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.SectorDTO;
import pe.datasys.model.SectorEntity;

public interface ISectorService extends ICRUD<SectorEntity, Integer> {
    Page<SectorDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);
}
