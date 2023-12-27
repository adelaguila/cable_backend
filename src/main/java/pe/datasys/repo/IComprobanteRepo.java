package pe.datasys.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.datasys.model.ComprobanteEntity;

public interface IComprobanteRepo extends IGenericRepo<ComprobanteEntity, String> {
    
    @Modifying
    @Query(value = "UPDATE comprobantes SET numero = :numero WHERE serie = :serie" , nativeQuery = true)
    Integer updateCorrelativo(@Param("numero") Integer numero, @Param("serie") String serie);
}
