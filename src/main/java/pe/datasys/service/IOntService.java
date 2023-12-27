package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.OntDTO;
import pe.datasys.model.OntEntity;

public interface IOntService extends ICRUD<OntEntity, String> {
    Page<OntDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);
    
    List<OntEntity> findByEstado(String estado);
}
