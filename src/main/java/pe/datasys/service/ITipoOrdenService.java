package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.TipoOrdenDTO;
import pe.datasys.model.TipoOrdenEntity;

public interface ITipoOrdenService extends ICRUD<TipoOrdenEntity, Integer> {
    Page<TipoOrdenDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);

    List<TipoOrdenEntity> findUsoEstadoAbonado(String usoEstadoAbonado);
}
