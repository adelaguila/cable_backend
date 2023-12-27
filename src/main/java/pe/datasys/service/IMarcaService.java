package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.MarcaDTO;
import pe.datasys.model.MarcaEntity;

public interface IMarcaService extends ICRUD<MarcaEntity, Integer> {
    Page<MarcaDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);
}
