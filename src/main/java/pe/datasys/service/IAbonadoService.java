package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.AbonadoDTO;
import pe.datasys.model.AbonadoEntity;
import pe.datasys.model.TerceroEntity;

public interface IAbonadoService extends ICRUD<AbonadoEntity, Long> {
    AbonadoEntity saveTransactional(TerceroEntity tercero, AbonadoEntity abonado);
    Page<AbonadoDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);

    Integer actualizarCargos();
}
