package pe.datasys.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.datasys.model.TerceroDireccionEntity;


public interface ITerceroDireccionRepo extends IGenericRepo<TerceroDireccionEntity, Long> {
    @Query(value = "SELECT * from terceros_direcciones where id_tercero = :idTercero order by id_tercero_direccion LIMIT 1" , nativeQuery = true)
    TerceroDireccionEntity direccionDefaultIdTercero(@Param("idTercero") Long idTercero);
}
