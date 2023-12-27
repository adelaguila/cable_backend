package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.CuentaBancariaDTO;
import pe.datasys.model.CuentaBancariaEntity;

public interface ICuentaBancariaService extends ICRUD<CuentaBancariaEntity, String> {
    Page<CuentaBancariaDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);
}
