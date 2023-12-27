package pe.datasys.service;
import pe.datasys.model.CargoEntity;

import java.util.List;

public interface ICargoService extends ICRUD<CargoEntity, Long> {
    // Page<OrdenDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);

     List<CargoEntity> findByAbonadoIdAbonado(Long idAbonado);

     CargoEntity saveCargoTransactional(CargoEntity cargo);
}
