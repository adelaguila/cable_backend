package pe.datasys.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.datasys.model.AbonadoEntity;


public interface IAbonadoRepo extends IGenericRepo<AbonadoEntity, Long> {
    @Modifying
    @Query(value = "UPDATE abonados SET fecha_activacion = :fechaActivacion, fecha_ultima_liquidacion = :fechaActivacion, estado = 'ACTIVADO' WHERE id_abonado = :idAbonado", nativeQuery = true)
    Integer activarAbonado(@Param("fechaActivacion") LocalDate fechaActivacion, @Param("idAbonado") Long idAbonado);

    @Modifying
    @Query(value = "UPDATE abonados SET fecha_ultima_liquidacion = :fechaUltimaLiquidacion, estado = 'CORTADO' WHERE id_abonado = :idAbonado", nativeQuery = true)
    Integer cortarAbonado(@Param("fechaUltimaLiquidacion") LocalDate fechaUltimaLiquidacion, @Param("idAbonado") Long idAbonado);

    @Query("SELECT a.idAbonado FROM AbonadoEntity a")
    List<Long> listaIdAbonados();

    @Query("FROM AbonadoEntity a where a.estado = :estado")
    List<AbonadoEntity> listaAbonadosEstado(@Param("estado") String estado);

    @Query("FROM AbonadoEntity a where a.fechaUltimaLiquidacion < :fechaCierre and a.estado = 'ACTIVADO'")
    List<AbonadoEntity> listaAbonadosGenerarCargos(@Param("fechaCierre") LocalDate fechaCierre);

    @Modifying
    @Query(value = "UPDATE abonados SET fecha_ultima_liquidacion = :fechaCierre WHERE fecha_ultima_liquidacion < :fechaCierre and estado = 'ACTIVADO'", nativeQuery = true)
    Integer actualizarFechaUltimaLiquidacionGenerarCargos(@Param("fechaCierre") LocalDate fechaCierre);

    @Query(value = "select a.id_abonado, \n" + //
            "\t(select sum(c.total) from cargos c where c.id_abonado = a.id_abonado) as totalCargos, \n" + //
            "\t(select sum(p.total) from pagos p where p.id_abonado = a.id_abonado) as totalPagos \n" + //
            "\tfrom abonados a", nativeQuery = true)
    List<Object[]> abonadosTotalesCargosPagos();

    @Modifying
    @Query(value = "update abonados a set \n" + //
            "deuda = if( \n" + //
            "\tif((select sum(c.total) from cargos c where c.id_abonado = a.id_abonado) > 0, (select sum(c.total) from cargos c where c.id_abonado = a.id_abonado), 0) - \n" + //
            "\tif((select sum(p.total) from pagos p where p.id_abonado = a.id_abonado) > 0, (select sum(p.total) from pagos p where p.id_abonado = a.id_abonado), 0) < 0, 0,\n" + //
            "\tif((select sum(c.total) from cargos c where c.id_abonado = a.id_abonado) > 0, (select sum(c.total) from cargos c where c.id_abonado = a.id_abonado), 0) - \n" + //
            "\tif((select sum(p.total) from pagos p where p.id_abonado = a.id_abonado) > 0, (select sum(p.total) from pagos p where p.id_abonado = a.id_abonado), 0)\t\n" + //
            "),\n" + //
            "saldo_favor = if( \n" + //
            "\tif((select sum(c.total) from cargos c where c.id_abonado = a.id_abonado) > 0, (select sum(c.total) from cargos c where c.id_abonado = a.id_abonado), 0) - \n" + //
            "\tif((select sum(p.total) from pagos p where p.id_abonado = a.id_abonado) > 0, (select sum(p.total) from pagos p where p.id_abonado = a.id_abonado), 0) < 0, \n" + //
            "\t(if((select sum(c.total) from cargos c where c.id_abonado = a.id_abonado) > 0, (select sum(c.total) from cargos c where c.id_abonado = a.id_abonado), 0) - \n" + //
            "\tif((select sum(p.total) from pagos p where p.id_abonado = a.id_abonado) > 0, (select sum(p.total) from pagos p where p.id_abonado = a.id_abonado), 0)) * -1, 0\t\n" + //
            ")", nativeQuery = true)
    Integer actualizarDeudaSaldoFavor();

    @Modifying
    @Query(value = "update abonados a set \n" + //
            "deuda = if( \n" + //
            "\tif((select sum(c.total) from cargos c where c.id_abonado = a.id_abonado) > 0, (select sum(c.total) from cargos c where c.id_abonado = a.id_abonado), 0) - \n" + //
            "\tif((select sum(p.total) from pagos p where p.id_abonado = a.id_abonado) > 0, (select sum(p.total) from pagos p where p.id_abonado = a.id_abonado), 0) < 0, 0,\n" + //
            "\tif((select sum(c.total) from cargos c where c.id_abonado = a.id_abonado) > 0, (select sum(c.total) from cargos c where c.id_abonado = a.id_abonado), 0) - \n" + //
            "\tif((select sum(p.total) from pagos p where p.id_abonado = a.id_abonado) > 0, (select sum(p.total) from pagos p where p.id_abonado = a.id_abonado), 0)\t\n" + //
            "),\n" + //
            "saldo_favor = if( \n" + //
            "\tif((select sum(c.total) from cargos c where c.id_abonado = a.id_abonado) > 0, (select sum(c.total) from cargos c where c.id_abonado = a.id_abonado), 0) - \n" + //
            "\tif((select sum(p.total) from pagos p where p.id_abonado = a.id_abonado) > 0, (select sum(p.total) from pagos p where p.id_abonado = a.id_abonado), 0) < 0, \n" + //
            "\t(if((select sum(c.total) from cargos c where c.id_abonado = a.id_abonado) > 0, (select sum(c.total) from cargos c where c.id_abonado = a.id_abonado), 0) - \n" + //
            "\tif((select sum(p.total) from pagos p where p.id_abonado = a.id_abonado) > 0, (select sum(p.total) from pagos p where p.id_abonado = a.id_abonado), 0)) * -1, 0\t\n" + //
            ") where a.id_abonado = :idAbonado", nativeQuery = true)
    Integer actualizarDeudaSaldoFavorIdAbonado(@Param("idAbonado") Long idAbonado);

}
