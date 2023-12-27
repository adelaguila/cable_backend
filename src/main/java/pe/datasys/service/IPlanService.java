package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.PlanDTO;
import pe.datasys.model.PlanEntity;

public interface IPlanService extends ICRUD<PlanEntity, Integer> {
    Page<PlanDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);
}
