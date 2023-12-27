package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.LiquidacionDTO;
import pe.datasys.model.LiquidacionEntity;

public interface ILiquidacionService extends ICRUD<LiquidacionEntity, Integer> {
    Page<LiquidacionDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);

    LiquidacionEntity saveLiquidacionTransactional(LiquidacionEntity liquidacion);
}
