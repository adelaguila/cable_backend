package pe.datasys.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

import pe.datasys.model.CargoEntity;

public interface ICargoRepo extends IGenericRepo<CargoEntity, Long> {

    @Query("FROM CargoEntity c WHERE c.abonado.idAbonado = :idAbonado ORDER BY c.fechaEmision")
    List<CargoEntity> findByAbonadoIdAbonado(@Param("idAbonado") Long idAbonado);

    @Modifying
    @Query(value = "UPDATE cargos SET pagado = pagado + :pagado WHERE id_cargo = :idCargo" , nativeQuery = true)
    Integer actualizarPago(@Param("pagado") BigDecimal pagado, @Param("idCargo") Long idCargo);

    @Query(value = "select * from cargos where total > pagado and id_abonado = :idAbonado order by fecha_emision" , nativeQuery = true)
    List<CargoEntity> cargosPendientesIdAbonado(@Param("idAbonado") Long idAbonado);
}
