package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.UbigeoDTO;
import pe.datasys.model.UbigeoEntity;

public interface IUbigeoService extends ICRUD<UbigeoEntity, String> {
    Page<UbigeoDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);

    List<UbigeoEntity> autocomplete(String term);
}
