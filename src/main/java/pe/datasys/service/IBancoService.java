package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.BancoDTO;
import pe.datasys.model.BancoEntity;

public interface IBancoService extends ICRUD<BancoEntity, Integer> {
    Page<BancoDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);
}
