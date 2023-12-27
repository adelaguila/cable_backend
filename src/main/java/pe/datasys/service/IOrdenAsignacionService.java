package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.OrdenAsignacionTablaDTO;
import pe.datasys.model.OrdenAsignacionEntity;

public interface IOrdenAsignacionService extends ICRUD<OrdenAsignacionEntity, Long> {
    OrdenAsignacionEntity findOrdenAsignacionActiva(Long idOrden);

    Page<OrdenAsignacionTablaDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);
}
