package pe.datasys.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.datasys.model.OrdenAsignacionProductoEntity;

public interface IOrdenAsignacionProductoRepo extends IGenericRepo<OrdenAsignacionProductoEntity, Long> {

    @Modifying
    @Query(value = "INSERT INTO ordenes_asignaciones_productos(id_orden_asignacion, id_producto, cantidad) VALUES (:idOrdenAsignacion, :idProducto, :cantidad)" , nativeQuery = true)
    Integer saveOrdenAsigancionProducto(@Param("idOrdenAsignacion") Long idOrdenAsignacion, @Param("idProducto") Integer idProducto, @Param("cantidad") Integer cantidad);
   
}
