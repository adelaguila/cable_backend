package pe.datasys.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import pe.datasys.model.TerceroEntity;

public interface ITerceroRepo extends IGenericRepo<TerceroEntity, Long> {

    @Query("FROM TerceroEntity t where t.dniruc =:dniruc")
    TerceroEntity checkDniruc(String dniruc);

    @Query("FROM TerceroEntity t WHERE t.dniruc LIKE %:term%  OR t.nombreTercero LIKE %:term% ORDER BY t.nombreTercero")
    List<TerceroEntity> autocomplete(String term);
}
