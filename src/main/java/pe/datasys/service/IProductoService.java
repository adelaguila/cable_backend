package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.ProductoDTO;
import pe.datasys.model.ProductoEntity;

public interface IProductoService extends ICRUD<ProductoEntity, Integer> {
    Page<ProductoDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);

    List<ProductoEntity> autocomplete(String term);
}
