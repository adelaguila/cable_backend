package pe.datasys.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.datasys.dto.OrdenAsignacionTablaDTO;
import pe.datasys.model.OrdenAsignacionEntity;

public interface IOrdenAsignacionRepo extends IGenericRepo<OrdenAsignacionEntity, Long> {

    @Modifying
    @Query(value = "UPDATE ordenes_asignaciones SET fecha_atencion = :fechaAtencion, reporte = :reporte WHERE id_orden = :idOrden AND id_user = :idUser AND activo = 1" , nativeQuery = true)
    Integer atenderOrdenAsignacion(@Param("fechaAtencion") LocalDate fechaAtencion, @Param("reporte") String reporte, @Param("idOrden") Long idOrden, @Param("idUser") Integer idUser);

    @Query("FROM OrdenAsignacionEntity u WHERE u.orden.idOrden = :idOrden and u.activo = 1")
    OrdenAsignacionEntity findOrdenAsignacionActiva(@Param("idOrden") Long idOrden);

    @Query("SELECT new pe.datasys.dto.OrdenAsignacionTablaDTO(o.idOrdenAsignacion, o.orden.idOrden, o.orden.fechaRegistro, o.fechaAsignacion, o.fechaAtencion, o.orden.abonado.tercero.nombreTercero, o.orden.tipoOrden.nombreTipoOrden, o.user.name, o.orden.abonado.sector.nombreSector, concat(o.orden.abonado.via.tipoVia.idTipoVia, '. ', o.orden.abonado.via.nombreVia, ' ', o.orden.abonado.numero)) FROM OrdenAsignacionEntity o WHERE o.orden.estado = 'ASIGNADO' ORDER BY o.fechaAsignacion")
    List<OrdenAsignacionTablaDTO> ordenesAsignadas();

    @Query("SELECT new pe.datasys.dto.OrdenAsignacionTablaDTO(o.idOrdenAsignacion, o.orden.idOrden, o.orden.fechaRegistro, o.fechaAsignacion, o.fechaAtencion, o.orden.abonado.tercero.nombreTercero, o.orden.tipoOrden.nombreTipoOrden, o.user.name, o.orden.abonado.sector.nombreSector, concat(o.orden.abonado.via.tipoVia.idTipoVia, '. ', o.orden.abonado.via.nombreVia, ' ', o.orden.abonado.numero)) FROM OrdenAsignacionEntity o WHERE o.orden.estado = 'ATENDIDO' ORDER BY o.fechaAtencion")
    List<OrdenAsignacionTablaDTO> ordenesAtendidas();
}
