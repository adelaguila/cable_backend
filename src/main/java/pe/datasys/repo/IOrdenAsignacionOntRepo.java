package pe.datasys.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.datasys.model.OrdenAsignacionOntEntity;

public interface IOrdenAsignacionOntRepo extends IGenericRepo<OrdenAsignacionOntEntity, Long> {

    @Modifying
    @Query(value = "INSERT INTO ordenes_asignaciones_onts(id_orden_asignacion, serie) VALUES (:idOrdenAsignacion, :serie)" , nativeQuery = true)
    Integer saveOrdenAsigancionOnt(@Param("idOrdenAsignacion") Long idOrdenAsignacion, @Param("serie") String serie);
   
}
