package pe.datasys.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.datasys.dto.ImprimirOrdenDTO;
import pe.datasys.model.OrdenEntity;

public interface IOrdenRepo extends IGenericRepo<OrdenEntity, Long> {
    @Query("SELECT new pe.datasys.dto.ImprimirOrdenDTO(o.idOrden, o.abonado.idAbonado, o.abonado.tercero.nombreTercero, o.detalle, o.abonado.via.nombreVia, o.abonado.referencia, o.reporte) FROM OrdenEntity o WHERE o.idOrden >= :idOrden ORDER BY o.idOrden")
    List<ImprimirOrdenDTO> findOrdenImprimir(@Param("idOrden") Long idOrden);

    List<OrdenEntity> findFirst2ByIdOrden(@Param("idOrden") Long idOrden);
    
    List<OrdenEntity> findFirst1ByIdOrden(@Param("idOrden") Long idOrden);

    @Query("FROM OrdenEntity o WHERE o.abonado.idAbonado = :idAbonado ORDER BY o.idOrden")
    List<OrdenEntity> findByAbonadoIdAbonado(@Param("idAbonado") Long idAbonado);

    @Modifying
    @Query(value = "UPDATE ordenes SET fecha_asignacion = :fechaAsignacion, estado = 'ASIGNADO' WHERE id_orden = :idOrden" , nativeQuery = true)
    Integer updateOrdenAsignacion(@Param("fechaAsignacion") LocalDate fechaAsignacion, @Param("idOrden") Long idOrden);

    @Modifying
    @Query(value = "UPDATE ordenes_asignaciones SET activo = 0 WHERE id_orden = :idOrden" , nativeQuery = true)
    Integer desactivarOrdenAsignaciones(@Param("idOrden") Long idOrden);

    @Modifying
    @Query(value = "UPDATE ordenes SET fecha_atencion = :fechaAtencion, estado = 'ATENDIDO' WHERE id_orden = :idOrden" , nativeQuery = true)
    Integer atenderOrdenAsignacion(@Param("fechaAtencion") LocalDate fechaAtencion, @Param("idOrden") Long idOrden);

}
