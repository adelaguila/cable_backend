package pe.datasys.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

import pe.datasys.model.PagoEntity;

public interface IPagoRepo extends IGenericRepo<PagoEntity, Long> {

    @Query("FROM PagoEntity p WHERE p.abonado.idAbonado = :idAbonado ORDER BY p.fechaPago")
    List<PagoEntity> findByAbonadoIdAbonado(@Param("idAbonado") Long idAbonado);

    @Query("SELECT SUM(p.total) FROM PagoEntity p WHERE p.abonado.idAbonado = :idAbonado")
    BigDecimal totalPagosIdAbonado(@Param("idAbonado") Long idAbonado);

}
