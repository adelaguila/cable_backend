package pe.datasys.repo;

import java.util.List;

import pe.datasys.model.TipoOrdenEntity;

public interface ITipoOrdenRepo extends IGenericRepo<TipoOrdenEntity, Integer> {

    List<TipoOrdenEntity> findByUsoEstadoAbonado(String usoEstadoAbonado);

}
