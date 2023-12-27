package pe.datasys.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.datasys.model.FacturacionItemEntity;

public interface IFacturacionItemRepo extends IGenericRepo<FacturacionItemEntity, Long> {
    @Query("FROM FacturacionItemEntity c WHERE c.facturacion.idFacturacion = :idFacturacion")
    List<FacturacionItemEntity> itemsIdFacturacion(@Param("idFacturacion") Long idFacturacion);
}
