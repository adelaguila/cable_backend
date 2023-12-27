package pe.datasys.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.datasys.model.OntEntity;

public interface IOntRepo extends IGenericRepo<OntEntity, String> {
    List<OntEntity> findByEstado(@Param("estado") String estado);

    @Modifying
    @Query(value = "UPDATE onts SET estado = :estado WHERE serie = :serie" , nativeQuery = true)
    Integer updateEstado(@Param("estado") String estado, @Param("serie") String serie);

}
